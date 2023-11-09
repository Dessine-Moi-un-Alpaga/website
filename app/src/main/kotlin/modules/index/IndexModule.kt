package be.alpago.website.modules.index

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.RestFirestoreCrudRepository
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.article.FirestoreArticleTransformer
import be.alpago.website.modules.highlight.FirestoreHighlightTransformer
import be.alpago.website.modules.highlight.Highlight
import be.alpago.website.modules.image.FirestoreImageMetadataTransformer
import be.alpago.website.modules.image.ImageMetadata
import io.ktor.client.HttpClient
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

fun indexModule() = module {
    single<CrudRepository<Article>>(
        named(IndexRepositories.article)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = IndexRepositories.article,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreArticleTransformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<CrudRepository<Highlight>>(
        named(IndexRepositories.guilds)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = IndexRepositories.guilds,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreHighlightTransformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<CrudRepository<Highlight>>(
        named(IndexRepositories.news)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = IndexRepositories.news,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreHighlightTransformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<CrudRepository<ImageMetadata>>(
        named(IndexRepositories.trainings)
    ) {
        val client by inject<HttpClient>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            RestFirestoreCrudRepository(
                client = client,
                collection = IndexRepositories.trainings,
                environment = environment.name,
                project = environment.project,
                transformer = FirestoreImageMetadataTransformer,
                url = environment.firestoreUrl,
            )
        )
    }

    single<IndexPageModelFactory> {
        val animalRepository by inject<CrudRepository<Animal>>(
            named(AnimalRepositories.animal)
        )
        val articleRepository by inject<CrudRepository<Article>>(
            named(IndexRepositories.article)
        )
        val guildRepository by inject<CrudRepository<Highlight>>(
            named(IndexRepositories.guilds)
        )
        val newsRepository by inject<CrudRepository<Highlight>>(
            named(IndexRepositories.news)
        )
        val trainingRepository by inject<CrudRepository<ImageMetadata>>(
            named(IndexRepositories.trainings)
        )

        IndexPageModelFactory(
            animalRepository = animalRepository,
            articleRepository = articleRepository,
            guildRepository = guildRepository,
            newsRepository= newsRepository,
            trainingRepository = trainingRepository,
        )
    }
}

fun Application.index() {
    koin {
        modules(indexModule())
    }

    indexRoutes()
}
