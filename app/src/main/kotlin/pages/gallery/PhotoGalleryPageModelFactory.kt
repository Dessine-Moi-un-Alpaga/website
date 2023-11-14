package be.alpago.website.pages.gallery

import be.alpago.website.domain.animal.Animal
import be.alpago.website.domain.image.ImageMetadata
import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.domain.ports.Repository

private const val DESCRIPTION = ""
private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.photos)}"

class PhotoGalleryPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val imageRepository: Repository<ImageMetadata>,
) {

    suspend fun create() : PageModel {
        val pageModel = PageModel(
            TITLE,
            DESCRIPTION,
            animals = animalRepository.findAll()
        )

        val images = imageRepository.findAll()
        pageModel.createPhotoGallerySection(
            color = SectionColor.WHITE,
            images = images,
            sectionTitle = "${Messages.photos}",
            subtitle = "${Messages.photoGallerySubtitle}",
            title = "${Messages.photoGalleryTitle}"
        )

        return pageModel
    }
}
