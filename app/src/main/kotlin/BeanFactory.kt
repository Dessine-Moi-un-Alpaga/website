package be.alpago.website

import kotlin.reflect.KClass

data class BeanFactoryKey(private val type: KClass<*>, private val name: String? = null)

typealias BeanFactory<T> = (String?) -> T

val beanFactories = mutableMapOf<BeanFactoryKey, BeanFactory<out Any>>()

val beans = mutableMapOf<BeanFactoryKey, Any>()

fun clear() {
    beans.clear()
    beanFactories.clear()
}

fun getEnvironmentVariable(name: String, default: String? = null) = System.getenv(name) ?: default ?: throw NoSuchEnvironmentVariableException(name)

inline fun <reified T : Any> register(name: String? = null, noinline block: BeanFactory<out T>) {
    val key = BeanFactoryKey(T::class, name)

    if (beanFactories.containsKey(key)) {
        throw DuplicateBeanException(key)
    }

    beanFactories[key] = block
}

inline fun <reified T : Any> inject(name: String? = null): T {
    val key = BeanFactoryKey(T::class, name)
    var bean = beans[key]

    if (bean == null) {
        val beanFactory = beanFactories[key] ?: throw NoSuchBeanException(key)
        bean = beanFactory.invoke(name)
        beans[key] = bean
    }

    return bean as T
}

class DuplicateBeanException(key: BeanFactoryKey) : Exception("$key")

class NoSuchBeanException(key: BeanFactoryKey) : Exception("$key")

class NoSuchEnvironmentVariableException(name: String) : Exception(name)
