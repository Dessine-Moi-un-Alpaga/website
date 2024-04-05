package be.alpago.website.libs.di

fun getEnvironmentVariable(name: String, default: String? = null) =
    System.getenv(name) ?:
        default ?:
            throw NoSuchEnvironmentVariableException(name)

class NoSuchEnvironmentVariableException(name: String) : Exception(name)
