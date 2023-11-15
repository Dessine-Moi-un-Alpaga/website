package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreArticleTransformer
import be.alpago.website.domain.Article
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val ARTICLE_TRANSFORMER = "articles"

fun Application.articleModule() {
    koin {
        modules(
            module {
                single<FirestoreAggregateTransformer<Article>>(
                    named(ARTICLE_TRANSFORMER)
                ) {
                    FirestoreArticleTransformer()
                }
            }
        )
    }
}
