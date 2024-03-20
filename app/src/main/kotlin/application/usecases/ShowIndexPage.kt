package be.alpago.website.application.usecases

import be.alpago.website.application.PageModel

fun interface ShowIndexPage {

    suspend fun execute(): PageModel
}

interface ShowIndexArticle

interface ShowIndexNewsHighlights

interface ShowIndexTrainingsPhotoGallery

interface ShowIndexGuildHighlights
