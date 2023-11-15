package be.alpago.website.adapters.firestore

import be.alpago.website.domain.Article

private const val ID = "id"
private const val BANNER = "banner"
private const val BANNER_DESCRIPTION = "bannerDescription"
private const val SECTION_TITLE = "sectionTitle"
private const val SUBTITLE = "subtitle"
private const val TEXT = "text"
private const val TITLE = "title"

class FirestoreArticleTransformer : FirestoreAggregateTransformer<Article> {

    override fun fromDomain(aggregateRoot: Article) = mapOf(
        ID to aggregateRoot.id,
        BANNER to aggregateRoot.banner,
        BANNER_DESCRIPTION to aggregateRoot.bannerDescription,
        SECTION_TITLE to aggregateRoot.sectionTitle,
        SUBTITLE to aggregateRoot.subtitle,
        TEXT to aggregateRoot.text,
        TITLE to aggregateRoot.title
    )

    override fun toDomain(fields: Map<String, Any?>) = Article(
        id = fields[ID] as String,
        banner = fields[BANNER] as String?,
        bannerDescription = fields[BANNER_DESCRIPTION] as String?,
        sectionTitle = fields[SECTION_TITLE] as String,
        subtitle = fields[SUBTITLE] as String?,
        text = fields[TEXT] as String,
        title = fields[TITLE] as String?
    )
}
