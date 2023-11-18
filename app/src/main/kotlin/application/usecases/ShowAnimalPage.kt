package be.alpago.website.application.usecases

import be.alpago.website.application.PageModel

fun interface ShowAnimalPage {

    suspend fun execute(id: String): PageModel
}
