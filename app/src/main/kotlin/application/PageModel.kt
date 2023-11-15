package be.alpago.website.application

import be.alpago.website.domain.Animal

class PageModel(
    val title: String,
    val description: String,
    animals: List<Animal>,
    val sections: List<SectionModel>,
) {
    val navigationModel = NavigationModel(animals)

    fun hasPhotoGallery() = sections.any { it.isPhotoGallery }
}
