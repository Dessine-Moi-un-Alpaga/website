package com.dessinemoiunalpaga.website.interfaces.ktor.routes

import com.dessinemoiunalpaga.website.application.usecases.ShowIndexArticle
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexGuildHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexNewsHighlights
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexPage
import com.dessinemoiunalpaga.website.application.usecases.ShowIndexTrainingsPhotoGallery
import com.dessinemoiunalpaga.website.domain.Article
import com.dessinemoiunalpaga.website.domain.Highlight
import com.dessinemoiunalpaga.website.domain.ImageMetadata
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.LayoutTemplate
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.TemplateProperties
import com.dessinemoiunalpaga.website.libs.domain.ports.persistence.Repository
import com.dessinemoiunalpaga.website.libs.ktor.routes.managementRoutes
import io.ktor.server.application.Application
import io.ktor.server.html.respondHtmlTemplate
import io.ktor.server.plugins.di.dependencies
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

/**
 * Registers the HTTP endpoints related to the index page:
 *
 * - `GET /`: returns the index page itself
 * - `GET /index.html`: returns the index page itself
 * - [Management Routes][managementRoutes] for the index page's [Article]:
 *     - `DELETE /api/index/article`
 *     - `DELETE /api/index/article/{id}`
 *     - `GET /api/index/article`
 *     - `GET /api/index/article/{id}`
 *     - `PUT /api/index/article`
 * - [Management Routes][managementRoutes] for the index page's guild [Highlight]s:
 *     - `DELETE /api/index/guilds`
 *     - `DELETE /api/index/guilds/{id}`
 *     - `GET /api/index/guilds`
 *     - `GET /api/index/guilds/{id}`
 *     - `PUT /api/index/guilds`
 * - [Management Routes][managementRoutes] for the index page's news [Highlight]s:
 *     - `DELETE /api/index/news`
 *     - `DELETE /api/index/news/{id}`
 *     - `GET /api/index/news`
 *     - `GET /api/index/news/{id}`
 *     - `PUT /api/index/news`
 * - [Management Routes][managementRoutes] for the index page's training photo gallery's [ImageMetadata]:
 *     - `DELETE /api/index/trainings`
 *     - `DELETE /api/index/trainings/{id}`
 *     - `GET /api/index/trainings`
 *     - `GET /api/index/trainings/{id}`
 *     - `PUT /api/index/trainings`
 */
suspend fun Application.indexRoutes() {
    val properties: TemplateProperties by dependencies
    val query: ShowIndexPage by dependencies

    routing {
        get(Regex("/(index.html)?")) {
            val pageModel = query.execute()
            val template = LayoutTemplate(properties, pageModel)
            call.respondHtmlTemplate(template) { }
        }
    }

    val articleRepository = dependencies.resolve<Repository<Article>>(ShowIndexArticle::class.simpleName)
    managementRoutes("/api/index/article", articleRepository)

    val guildRepository = dependencies.resolve<Repository<Highlight>>(ShowIndexGuildHighlights::class.simpleName)
    managementRoutes("/api/index/guilds", guildRepository)

    val newsRepository = dependencies.resolve<Repository<Highlight>>(ShowIndexNewsHighlights::class.simpleName)
    managementRoutes("/api/index/news", newsRepository)

    val trainingRepository = dependencies.resolve<Repository<ImageMetadata>>(ShowIndexTrainingsPhotoGallery::class.simpleName)
    managementRoutes("/api/index/trainings", trainingRepository)
}
