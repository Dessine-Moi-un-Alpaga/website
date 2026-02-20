package be.alpago.website.interfaces.ktor

data class AuthenticationProperties(

    /**
     * Credentials for accessing this website's API.
     *
     * Each line represents a username and a [bcrypt](https://en.wikipedia.org/wiki/Bcrypt) password hash, seperated by a colon.
     */
    val credentials: String
)
