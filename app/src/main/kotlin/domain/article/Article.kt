package be.alpago.website.domain.article

import be.alpago.website.libs.domain.AggregateRoot
import kotlinx.serialization.Serializable

@Serializable
data class Article(
    override val id: String,
    val banner: String?,
    val bannerDescription: String?,
    val sectionTitle: String,
    val subtitle: String?,
    val text: String,
    val title: String?,
) : AggregateRoot
