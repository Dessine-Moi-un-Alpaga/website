package libs.repository

import be.alpago.website.libs.domain.AggregateRoot
import be.alpago.website.libs.domain.AggregateRootNotFound
import be.alpago.website.libs.repository.FirestoreAggregateTransformer
import be.alpago.website.libs.repository.RestFirestoreRepository
import be.alpago.website.libs.repository.createClient
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
    override val id: String = UUID.randomUUID().toString(),
    val intField: Int? = 0,
    val stringField: String = UUID.randomUUID().toString(),
) : AggregateRoot

class TestTransformer : FirestoreAggregateTransformer<TestAggregateRoot> {

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

    private val repository: RestFirestoreRepository<TestAggregateRoot>

    init {
        val client = createClient()
        repository = RestFirestoreRepository(
            collection = "test",
            client = client,
            environment = "test",
            project = "gitops-8ab10a6068",
            transformer = TestTransformer(),
            url = "http://127.0.0.1:8181",
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
            repository.create(aggregateRoot)
            repository.get(aggregateRoot.id) shouldBe aggregateRoot
        }
    }

    @Test
    fun `a document can be updated`() {
        val aggregateRoot = TestAggregateRoot()

        runBlocking {
            repository.create(aggregateRoot)
            val updated = TestAggregateRoot(aggregateRoot.id)
            repository.create(updated)
            repository.get(aggregateRoot.id) shouldBe updated
        }
    }

    @Test
    fun `a document can be deleted`() {
        val aggregateRoot = TestAggregateRoot()

        runBlocking {
            repository.create(aggregateRoot)
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
            repository.create(aggregateRoot1)
            repository.create(aggregateRoot2)
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
            repository.create(aggregateRoot1)
            repository.create(aggregateRoot2)
            repository.create(aggregateRoot3)
            val aggregateRoots = repository.findBy("stringField", aggregateRoot2.stringField)
            aggregateRoots shouldContainAll listOf(
                aggregateRoot2,
                aggregateRoot3
            )
        }
    }
}
