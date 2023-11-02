package be.alpago.website.modules.gallery

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.model.SectionColor
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.image.ImageMetadata

class PhotoGalleryPageModelFactory(
    private val animalRepository: CrudRepository<Animal>,
    private val imageRepository: CrudRepository<ImageMetadata>,
) {

    suspend fun create() : PageModel {
        val pageModel = PageModel(
            "${Messages.dmua} :: ${capitalize(Messages.photos)}",
            "",
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
