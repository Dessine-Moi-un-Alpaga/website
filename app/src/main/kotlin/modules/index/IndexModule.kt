package be.alpago.website.modules.index

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.repository.CachingCrudRepository
import be.alpago.website.libs.repository.CrudRepository
import be.alpago.website.libs.repository.FirestorePageCollection
import be.alpago.website.modules.animal.Animal
import be.alpago.website.modules.animal.AnimalRepositories
import be.alpago.website.modules.article.Article
import be.alpago.website.modules.article.FirestoreArticleRepository
import be.alpago.website.modules.highlight.FirestoreHighlightRepository
import be.alpago.website.modules.highlight.Highlight
import be.alpago.website.modules.image.FirestoreImageMetadataRepository
import be.alpago.website.modules.image.ImageMetadata
import com.google.cloud.firestore.Firestore
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

private const val DOCUMENT = "index"

private fun createCollection(collection: String) = Pair(DOCUMENT, collection)

fun indexModule() = module {
    single<CrudRepository<Article>>(
        named(IndexRepositories.article)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreArticleRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                createCollection("article"),
            )
        )
    }

    single<CrudRepository<Highlight>>(
        named(IndexRepositories.guilds)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreHighlightRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                createCollection("guilds"),
            )
        )

    }

    single<CrudRepository<Highlight>>(
        named(IndexRepositories.news)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreHighlightRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                createCollection("news"),
            )
        )
    }

    single<CrudRepository<ImageMetadata>>(
        named(IndexRepositories.trainings)
    ) {
        val db by inject<Firestore>()
        val environment by inject<Environment>()
        CachingCrudRepository(
            FirestoreImageMetadataRepository(
                collection = FirestorePageCollection.name,
                db = db,
                environment = environment.name,
                createCollection("trainings"),
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
