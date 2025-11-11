package be.alpago.website.adapters.jakarta.mail

data class JakartaMailProperties(
    val address: String,
    val smtpServerAddress: String,
    val smtpServerPassword: String,
    val smtpServerPort : Int,
    val smtpServerUsername: String,
)
