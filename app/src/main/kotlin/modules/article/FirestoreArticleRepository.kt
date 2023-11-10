package be.alpago.website.modules.article

import be.alpago.website.libs.repository.FirestoreAggregateTransformer

private object Fields {
    const val id = "id"
    const val banner = "banner"
    const val bannerDescription = "bannerDescription"
    const val sectionTitle = "sectionTitle"
    const val subtitle = "subtitle"
    const val text = "text"
    const val title = "title"
}

object FirestoreArticleTransformer : FirestoreAggregateTransformer<Article> {

    override fun fromDomain(aggregateRoot: Article) = mapOf(
        Fields.id to aggregateRoot.id,
        Fields.banner to aggregateRoot.banner,
        Fields.bannerDescription to aggregateRoot.bannerDescription,
        Fields.sectionTitle to aggregateRoot.sectionTitle,
        Fields.subtitle to aggregateRoot.subtitle,
        Fields.text to aggregateRoot.text,
        Fields.title to aggregateRoot.title
    )

    override fun toDomain(fields: Map<String, Any?>) = Article(
        id = fields[Fields.id] as String,
        banner = fields[Fields.banner] as String?,
        bannerDescription = fields[Fields.bannerDescription] as String?,
        sectionTitle = fields[Fields.sectionTitle] as String,
        subtitle = fields[Fields.subtitle] as String?,
        text = fields[Fields.text] as String,
        title = fields[Fields.title] as String?
    )
}
