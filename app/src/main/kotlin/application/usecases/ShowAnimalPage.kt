package com.dessinemoiunalpaga.website.application.usecases

import com.dessinemoiunalpaga.website.application.PageModel

fun interface ShowAnimalPage {

    suspend fun execute(id: String): PageModel
}
