package com.dessinemoiunalpaga.website.application.usecases

import com.dessinemoiunalpaga.website.application.PageModel

fun interface ShowPhotoGalleryPage {

    suspend fun execute(): PageModel
}
