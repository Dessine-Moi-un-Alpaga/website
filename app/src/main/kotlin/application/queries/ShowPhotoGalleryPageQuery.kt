package be.alpago.website.application.queries

import be.alpago.website.application.Messages
import be.alpago.website.application.PageModel
import be.alpago.website.application.PhotoGallerySectionModel
import be.alpago.website.application.SectionColor
import be.alpago.website.application.usecases.ShowPhotoGalleryPage
import be.alpago.website.domain.Animal
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize
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
