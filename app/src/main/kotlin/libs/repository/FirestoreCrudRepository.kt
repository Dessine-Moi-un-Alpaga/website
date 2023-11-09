package be.alpago.website.libs.repository

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.AggregateRootNotFound
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

class RestFirestoreCrudRepository<T : AggregateRoot>(
    private val client: HttpClient,
    private val collection: String,
    environment: String,
    project: String,
    private val transformer: FirestoreAggregateTransformer<T>,
    url: String,
) : CrudRepository<T> {

    private val baseUrl = "${url}/v1/projects/${project}/databases/(default)/documents/environments/${environment}"

    private val rootUrl = "${baseUrl}/${collection}"

    override suspend fun create(aggregateRoot: T) {
        val fields = transformer.toFirestore(aggregateRoot)
        val document = Document(fields)
        client.patch("${rootUrl}/${aggregateRoot.id}") {
            setBody(document)
        }
    }

    override suspend fun delete(id: String) {
        client.delete("${rootUrl}/${id}")
    }

    override suspend fun deleteAll() {
        val response = client.get("${rootUrl}?mask.fieldPaths=id")

        if (response.status == HttpStatusCode.OK) {
            val body = response.body<ListDocumentsResponse>()

            for (document in body.documents) {
                val id = (document.fields["id"]!! as StringValue).stringValue
                client.delete("${rootUrl}/${id}")
            }
        }
    }

    override suspend fun findAll(): List<T> {
        val response = client.get(rootUrl).body<ListDocumentsResponse>()

        return response.documents.map { transformer.fromFirestore(it.fields) }
    }

    override suspend fun findBy(field: String, value: String): List<T> {
        val request = Query(
            structuredQuery = StructuredQuery(
                from = listOf(
                    CollectionSelector(collection)
                ),
                where = Filter(
                    fieldFilter = FieldFilter(
                        field = FieldReference(field),
                        op = Operator.EQUAL,
                        value = StringValue(stringValue = value)
                    )
                )
            )
        )

        val response = client.post("${baseUrl}:runQuery") {
            setBody(request)
        }

        return response.body<List<QueryResponse>>()
            .filter { it.document != null }
            .map { transformer.fromFirestore(it.document!!.fields) }
    }

    override suspend fun get(id: String): T {
        val response = client.get("${rootUrl}/${id}")

        if (response.status == HttpStatusCode.NotFound) {
            throw AggregateRootNotFound()
        }

        val document = response.body<Document>()

        return transformer.fromFirestore(document.fields)
    }
}

@Serializable
private data class Query(
    val structuredQuery: StructuredQuery,
)

@Serializable
private data class CollectionSelector(
    private val collectionId: String
)

@Serializable
private data class StructuredQuery(
    val from: List<CollectionSelector>,
    val where: Filter,
)

@Serializable
private data class Filter(val fieldFilter: FieldFilter)

@Serializable
private data class FieldFilter(
    val field: FieldReference,
    val op: Operator,
    val value: Value,
)

@Serializable
private data class FieldReference(val fieldPath: String)

@Serializable
private enum class Operator {
    EQUAL
}

@Serializable
private data class ListDocumentsResponse(val documents: List<Document> = listOf())

@Serializable
private data class Document(
    val fields: Map<String, Value> = mapOf(),
)

@Serializable
private data class QueryResponse(
    val document: Document? = null
)
