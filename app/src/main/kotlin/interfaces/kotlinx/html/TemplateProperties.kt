package be.alpago.website.interfaces.kotlinx.html

/**
 * Configuration properties used by the html templates.
 */
data class TemplateProperties(
    /**
     * The base URL for the remotely stored assets.
     */
    val baseAssetUrl: String,
    /**
     * The email contact address to which the emails should be sent from the website.
     */
    val emailAddress: String,
    /**
     * Whether to include test identifiers in the html code.
     */
    val includeTestIds: Boolean,
)
