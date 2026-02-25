package be.alpago.website.application

import be.alpago.website.domain.Animal

/**
 * The model for every page on the website.
 */
class PageModel(
    val title: String,
    val description: String,
    animals: List<Animal>,
    /**
     * The page contents, organized as sections.
     */
    val sections: List<SectionModel>,
) {
    /**
     * Can be used on each page to navigate the contents of the website.
     */
    val navigationModel = NavigationModel(animals)

    /**
     * Whether this page includes a gallery of photographs.
     */
    fun hasPhotoGallery() = sections.any { it.isPhotoGallery }
}
