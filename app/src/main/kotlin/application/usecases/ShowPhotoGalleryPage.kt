package be.alpago.website.application.usecases

import be.alpago.website.application.PageModel

fun interface ShowPhotoGalleryPage {

    suspend fun execute(): PageModel
}
