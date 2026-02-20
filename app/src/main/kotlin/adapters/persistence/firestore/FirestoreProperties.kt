package be.alpago.website.adapters.persistence.firestore

data class FirestoreProperties(
    val environmentName: String,
    val project: String,
    val url: String,
)
