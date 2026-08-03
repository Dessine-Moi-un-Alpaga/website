package be.alpago.website.interfaces.ktor

data class AuthenticationProperties(

    /**
     * Bcrypt hash of the key for accessing this website's API.
     */
    val apiKeyHash: String,
)
