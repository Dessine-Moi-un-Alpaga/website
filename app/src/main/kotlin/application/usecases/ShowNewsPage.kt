package be.alpago.website.application.usecases

import be.alpago.website.application.PageModel

fun interface ShowNewsPage {

    suspend fun execute(): PageModel
}
