package com.dessinemoiunalpaga.website.application.queries

import com.dessinemoiunalpaga.website.application.PageModel
import com.dessinemoiunalpaga.website.application.PhotoGallerySectionModel
import com.dessinemoiunalpaga.website.application.SectionColor
import com.dessinemoiunalpaga.website.application.usecases.ShowPhotoGalleryPage
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.domain.ImageMetadata
import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

private const val DESCRIPTION = ""

private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.photos)}"

class  ShowPhotoGalleryPageQuery(
    private val animalRepository: Repository<Animal>,
    private val imageRepository: Repository<ImageMetadata>,
) : ShowPhotoGalleryPage {

    override suspend fun execute() = coroutineScope {
        val animals = async { animalRepository.findAll() }
        val images = async { imageRepository.findAll() }

        PageModel(
            animals = animals.await(),
            description = DESCRIPTION,
            sections = listOf(
                PhotoGallerySectionModel(
                    color = SectionColor.WHITE,
                    id = "photos",
                    images = images.await(),
                    sectionTitle = "${Messages.photos}",
                    subtitle = "${Messages.photoGallerySubtitle}",
                    title = "${Messages.photoGalleryTitle}",
                )
            ),
            title = TITLE,
        )
    }
}
