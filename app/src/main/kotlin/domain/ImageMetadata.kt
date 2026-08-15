package com.dessinemoiunalpaga.website.domain

import com.dessinemoiunalpaga.website.libs.domain.AggregateRoot
import kotlinx.serialization.Serializable

@Serializable
data class ImageMetadata(
    override val id: String,
    val description: String,
    val height: Int,
    val path: String,
    val thumbnailPath: String,
    val width: Int,
) : AggregateRoot
