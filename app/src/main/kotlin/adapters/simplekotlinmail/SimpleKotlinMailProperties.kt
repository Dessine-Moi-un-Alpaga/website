package be.alpago.website.adapters.simplekotlinmail

data class SimpleKotlinMailProperties(
    val address: String,
    val smtpServerAddress: String,
    val smtpServerPassword: String,
    val smtpServerPort : Int,
    val smtpServerUsername: String,
)
