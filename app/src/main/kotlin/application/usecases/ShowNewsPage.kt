package com.dessinemoiunalpaga.website.application.usecases

import com.dessinemoiunalpaga.website.application.PageModel

fun interface ShowNewsPage {

    suspend fun execute(): PageModel
}
