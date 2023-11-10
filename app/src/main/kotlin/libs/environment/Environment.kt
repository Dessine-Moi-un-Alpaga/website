package be.alpago.website.libs.environment

data class Environment(
    val baseAssetUrl: String,
    val credentials: String,
    val emailAddress: String,
    val firestoreUrl: String,
    val name: String,
    val project: String,
    val sendGridApiKey: String,
)
