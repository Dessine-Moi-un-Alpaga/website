package be.alpago.website.modules.gallery

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
            "Dessine-Moi un Alpaga :: Photos",
            "",
            animals = animalRepository.findAll()
        )

        val images = imageRepository.findAll()
        pageModel.createPhotoGallerySection(
            color = SectionColor.WHITE,
            images = images,
            sectionTitle = "Photos",
            subtitle = "Une sélection de nos plus beaux moments avec le troupeau",
            title = "Photographie-moi un alpaga"
        )

        return pageModel
    }
}
