package com.dessinemoiunalpaga.website.application.usecases

import com.dessinemoiunalpaga.website.application.PageModel

fun interface ShowIndexPage {

    suspend fun execute(): PageModel
}

interface ShowIndexArticle

interface ShowIndexNewsHighlights

interface ShowIndexTrainingsPhotoGallery

interface ShowIndexGuildHighlights
