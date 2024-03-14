package be.alpago.website.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreArticleTransformer
import be.alpago.website.domain.Article
import be.alpago.website.register
import io.ktor.server.application.Application

const val ARTICLE_TRANSFORMER = "articles"

fun Application.articleModule() {
    register<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER) {
        FirestoreArticleTransformer()
    }
}
