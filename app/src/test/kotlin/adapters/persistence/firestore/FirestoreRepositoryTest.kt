package be.alpago.website.adapters.persistence.firestore

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.ports.persistence.AggregateRootNotFound
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

private object TestFields {

    const val id = "id"
    const val intField = "intField"
    const val stringField = "stringField"
}

@Serializable
data class TestAggregateRoot(
    override val id: String = "${UUID.randomUUID()}",
    val intField: Int? = 0,
    val stringField: String = "${UUID.randomUUID()}",
) : AggregateRoot

class TestTransformer : FirestoreAggregateTransformer<TestAggregateRoot>() {

    override fun fromDomain(aggregateRoot: TestAggregateRoot) = mapOf(
        TestFields.id to aggregateRoot.id,
        TestFields.intField to aggregateRoot.intField,
        TestFields.stringField to aggregateRoot.stringField,
    )

    override fun toDomain(fields: Map<String, Any?>) = TestAggregateRoot(
        id = fields[TestFields.id] as String,
        intField = fields[TestFields.intField] as Int?,
        stringField = fields[TestFields.stringField] as String,
    )
}

class FirestoreRepositoryTest {

    private val repository: FirestoreRepository<TestAggregateRoot>

    init {
        val client = firestoreHttpClient()
        repository = FirestoreRepository(
            collection = "test",
            client = client,
            properties = FirestoreProperties(
                environmentName = "test",
                project = "gitops-8ab10a6068",
                url = "http://127.0.0.1:8181",
            ),
            transformer = TestTransformer(),
        )
    }

    @BeforeEach
    fun `empty collection`() {
        runBlocking {
            repository.deleteAll()
        }
    }

    @Test
    fun `a document can be created`() {
        val aggregateRoot = TestAggregateRoot(intField = null)

        runBlocking {
            repository.save(aggregateRoot)
            repository.get(aggregateRoot.id) shouldBe aggregateRoot
        }
    }

    @Test
    fun `a document can be updated`() {
        val aggregateRoot = TestAggregateRoot()

        runBlocking {
            repository.save(aggregateRoot)
            val updated = TestAggregateRoot(aggregateRoot.id)
            repository.save(updated)
            repository.get(aggregateRoot.id) shouldBe updated
        }
    }

    @Test
    fun `a document can be deleted`() {
        val aggregateRoot = TestAggregateRoot()

        runBlocking {
            repository.save(aggregateRoot)
            repository.delete(aggregateRoot.id)

            shouldThrow<AggregateRootNotFound> {
                repository.get(aggregateRoot.id)
            }
        }
    }

    @Test
    fun `documents can be listed`() {
        val aggregateRoot1 = TestAggregateRoot()
        val aggregateRoot2 = TestAggregateRoot()

        runBlocking {
            repository.save(aggregateRoot1)
            repository.save(aggregateRoot2)
            val aggregateRoots = repository.findAll()
            aggregateRoots shouldContainAll listOf(
                aggregateRoot1,
                aggregateRoot2
            )
        }
    }

    @Test
    fun `documents can be queried by field value`() {
        val aggregateRoot1 = TestAggregateRoot()
        val aggregateRoot2 = TestAggregateRoot()
        val aggregateRoot3 = TestAggregateRoot(stringField = aggregateRoot2.stringField)

        runBlocking {
            repository.save(aggregateRoot1)
            repository.save(aggregateRoot2)
            repository.save(aggregateRoot3)
            val aggregateRoots = repository.findBy("stringField", aggregateRoot2.stringField)
            aggregateRoots shouldContainAll listOf(
                aggregateRoot2,
                aggregateRoot3
            )
        }
    }
}
