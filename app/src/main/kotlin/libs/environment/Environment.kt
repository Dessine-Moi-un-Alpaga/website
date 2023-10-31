package be.alpago.website.libs.environment

data class Environment(
    val baseAssetUrl: String,
    val emailAddress: String,
    val emailSubject: String,
    val name: String,
    val credentials: String,
    val project: String,
    val sendGridApiKey: String,
)
