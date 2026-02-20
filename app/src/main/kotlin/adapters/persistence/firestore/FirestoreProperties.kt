package be.alpago.website.adapters.persistence.firestore

data class FirestoreProperties(
    /**
     * Used to discriminate between the development and production Firestore collections.
     */
    val environmentName: String,
    /**
     * Mandatory part of all Firestore collection paths. The project is always the same but this can be configured as
     * hardcoding the project id in the code would definitely be awkward.
     */
    val project: String,
    /**
     * Usually `https://firestore.googleapis.com`, but can be overridden, for instance to target a local
     * emulator.
     */
    val url: String,
)
