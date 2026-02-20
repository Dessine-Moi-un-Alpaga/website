package be.alpago.website.adapters.persistence.firestore

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.ports.persistence.AggregateRootNotFound
import be.alpago.website.libs.domain.ports.persistence.Repository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.Serializable

private const val ID_FIELD = "id"
private const val NEXT_PAGE_PARMAETER = "pageToken"

class FirestoreRepository<T : AggregateRoot>(
    private val client: HttpClient,
    private val collection: String,
    properties: FirestoreProperties,
    private val transformer: FirestoreAggregateTransformer<T>,
) : Repository<T> {

    private val baseUrl = "${properties.url}/v1/projects/${properties.project}/databases/(default)/documents/environments/${properties.environmentName}"

    private val rootUrl = "${baseUrl}/${collection}"

    override suspend fun create(aggregateRoot: T) {
        val fields = transformer.toFirestore(aggregateRoot)
        val document = Document(fields)
        val response = client.patch("${rootUrl}/${aggregateRoot.id}") {
            setBody(document)
        }

        if (response.status != HttpStatusCode.OK) {
            throw RuntimeException("${response.status}")
        }
    }

    override suspend fun delete(id: String) {
        val response = client.delete("${rootUrl}/${id}")

        if (response.status != HttpStatusCode.OK) {
            throw RuntimeException("${response.status}")
        }
    }

    override suspend fun deleteAll() {
        var nextPageToken: String? = null

        do {
            val response = client.get(rootUrl) {
                parameter("mask.fieldPaths", ID_FIELD)

                if (nextPageToken != null) {
                    parameter(NEXT_PAGE_PARMAETER, nextPageToken)
                }
            }

            if (response.status == HttpStatusCode.OK) {
                val body = response.body<ListDocumentsResponse>()

                for (document in body.documents) {
                    val idValue = document.fields[ID_FIELD]

                    if (idValue != null) {
                        val id = (idValue as StringValue).stringValue
                        delete(id)
                    }
                }

                nextPageToken = body.nextPageToken
            }
        } while (nextPageToken != null)
    }

    override suspend fun findAll(): List<T> {
        val result = mutableListOf<T>()
        var nextPageToken: String? = null

        do {
            val response = client.get(rootUrl) {
                if (nextPageToken != null) {
                    parameter(NEXT_PAGE_PARMAETER, nextPageToken)
                }
            }

            if (response.status != HttpStatusCode.OK) {
                throw RuntimeException("${response.status}")
            }

            val body = response.body<ListDocumentsResponse>()
            result.addAll(
                body.documents.map {
                    transformer.fromFirestore(it.fields)
                }
            )
            nextPageToken = body.nextPageToken
        } while (nextPageToken != null)

        return result.toList()
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

        if (response.status != HttpStatusCode.OK) {
            throw RuntimeException("${response.status}")
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
    private val collectionId: String,
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
    EQUAL,
}

@Serializable
private data class ListDocumentsResponse(
    val documents: List<Document> = listOf(),
    val nextPageToken: String? = null,
)

@Serializable
private data class Document(
    val fields: Map<String, Value> = mapOf(),
)

@Serializable
private data class QueryResponse(
    val document: Document? = null,
)
