package be.alpago.website.application

import be.alpago.website.domain.Animal
import be.alpago.website.domain.ImageMetadata
import be.alpago.website.libs.domain.ports.Repository
import be.alpago.website.libs.kotlin.i18n.capitalize

private const val DESCRIPTION = ""

private val TITLE = "${Messages.dmua} :: ${capitalize(Messages.photos)}"

class  PhotoGalleryPageModelFactory(
    private val animalRepository: Repository<Animal>,
    private val imageRepository: Repository<ImageMetadata>,
) {

    suspend fun create() : PageModel {
        val animals = animalRepository.findAll()
        val images = imageRepository.findAll()

        return PageModel(
            animals = animals,
            description = DESCRIPTION,
            sections = listOf(
                PhotoGallerySectionModel(
                    color = SectionColor.WHITE,
                    images = images,
                    sectionTitle = "${Messages.photos}",
                    subtitle = "${Messages.photoGallerySubtitle}",
                    title = "${Messages.photoGalleryTitle}",
                )
            ),
            title = TITLE,
        )
    }
}
