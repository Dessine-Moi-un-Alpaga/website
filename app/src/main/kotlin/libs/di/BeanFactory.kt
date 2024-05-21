package be.alpago.website.libs.di

import kotlin.reflect.KClass

data class BeanFactoryKey(private val type: KClass<*>, private val useCase: KClass<*>? = null)

typealias BeanFactory<T> = (KClass<*>?) -> T

val beanFactories = mutableMapOf<BeanFactoryKey, BeanFactory<out Any>>()

val beans = mutableMapOf<BeanFactoryKey, Any>()

fun clear() {
    beans.clear()
    beanFactories.clear()
}

inline fun <reified T : Any> register(useCase: KClass<*>? = null, noinline block: BeanFactory<T>) {
    val key = BeanFactoryKey(T::class, useCase)

    if (beanFactories.containsKey(key)) {
        throw DuplicateBeanException(key)
    }

    beanFactories[key] = block
}

inline fun <reified T : Any> mock(useCase: KClass<*>? = null, noinline block: BeanFactory<T>) {
    val key = BeanFactoryKey(T::class, useCase)
    beanFactories[key] = block
}

inline fun <reified T : Any> inject(useCase: KClass<*>? = null): T {
    val key = BeanFactoryKey(T::class, useCase)
    var bean = beans[key]

    if (bean == null) {
        val beanFactory = beanFactories[key] ?: throw NoSuchBeanException(key)
        bean = beanFactory.invoke(useCase)
        beans[key] = bean
    }

    return bean as T
}

class DuplicateBeanException(key: BeanFactoryKey) : Exception("$key")

class NoSuchBeanException(key: BeanFactoryKey) : Exception("$key")
