package be.alpago.website.interfaces.ktor

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.UUID

interface TestBeanInterface

data class TestBean(private val id: String = "${UUID.randomUUID()}") : TestBeanInterface

data class Wrapper<T>(private val delegate: T)

class BeanFactoryTest {

    @BeforeEach
    fun `clear beans`() {
        clear()
    }

    @Test
    fun `trying to inject an unexisting bean throws an exception`() {
        shouldThrow<NoSuchBeanException> {
            inject<TestBean>()
        }
    }

    @Test
    fun `beans can be registered with no type argument and injected`() {
        val expected = TestBean()

        register {
            expected
        }

        val actual = inject<TestBean>()

        actual shouldBeEqual expected
    }

    @Test
    fun `duplicate beans cannot be created`() {
        register {
            TestBean()
        }

        shouldThrow<DuplicateBeanException> {
            register {
                TestBean()
            }
        }
    }

    @Test
    fun `beans can be registered with a type argument and injected`() {
        val expected = TestBean()

        register<TestBean> {
            expected
        }

        val actual = inject<TestBean>()

        actual shouldBeEqual expected
    }

    @Test
    fun `beans can be registered with a supertype argument and injected using the same type`() {
        val expected = TestBean()

        register<TestBeanInterface> {
            expected
        }

        val actual = inject<TestBeanInterface>()

        actual shouldBeEqual expected
    }

    @Test
    fun `the same bean can be registered multiple times with different types`() {
        val expected = TestBean()

        register<TestBean> {
            expected
        }

        register<TestBeanInterface> {
            expected
        }

        inject<TestBeanInterface>() shouldBe expected
        inject<TestBean>() shouldBe expected
    }

    @Test
    fun `beans of the same type can be further discriminated with a name`() {
        val expected1 = TestBean()
        val expected2 = TestBean()

        register<TestBeanInterface>("expected1") {
            expected1
        }

        register<TestBeanInterface>("expected2") {
            expected2
        }

        shouldThrow<NoSuchBeanException> {
            inject<TestBeanInterface>()
        }

        val actual1 = inject<TestBeanInterface>("expected1")
        val actual2 = inject<TestBeanInterface>("expected2")

        actual1 shouldBeEqual expected1
        actual2 shouldBeEqual expected2
    }

    @Test
    fun `beans are cached`() {
        register {
            TestBean()
        }
        val first = inject<TestBean>()
        val second = inject<TestBean>()

        first shouldBe second
    }

    @Test
    fun `generic beans cannot be discriminated by their type arguments`() {
        register {
            Wrapper(TestBean())
        }

        shouldThrow<DuplicateBeanException> {
            register {
                Wrapper<TestBeanInterface>(TestBean())
            }
        }
    }
}
