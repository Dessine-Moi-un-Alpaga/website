package be.alpago.website.domain.highlight

import be.alpago.website.libs.domain.AggregateRoot
import kotlinx.serialization.Serializable

@Serializable
data class Highlight(
    override val id: String,
    val link: String,
    val text: String?,
    val thumbnail: String,
    val thumbnailDescription: String,
    val title: String,
) : AggregateRoot
