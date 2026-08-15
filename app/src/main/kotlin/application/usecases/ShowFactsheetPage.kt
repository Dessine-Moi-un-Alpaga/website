package com.dessinemoiunalpaga.website.application.usecases

import com.dessinemoiunalpaga.website.application.PageModel

fun interface ShowFactsheetPage {

    suspend fun execute(): PageModel
}

interface ShowFactsheetArticle

interface ShowFactsheetHighlights
