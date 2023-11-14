package be.alpago.website.modules.article

import be.alpago.website.domain.article.Article
import be.alpago.website.domain.article.FirestoreArticleTransformer
import be.alpago.website.libs.firestore.FirestoreAggregateTransformer
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val ARTICLE_TRANSFORMER = "articles"

private fun articleModule() = module {
    single<FirestoreAggregateTransformer<Article>>(
        named(ARTICLE_TRANSFORMER)
    ) {
        FirestoreArticleTransformer()
    }
}

fun Application.articles() {
    koin {
        modules(articleModule())
    }
}
