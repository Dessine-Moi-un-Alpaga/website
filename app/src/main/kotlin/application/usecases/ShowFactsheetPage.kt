package be.alpago.website.application.usecases

import be.alpago.website.application.PageModel

fun interface ShowFactsheetPage {

    suspend fun execute(): PageModel
}

interface ShowFactsheetArticle

interface ShowFactsheetHighlights
