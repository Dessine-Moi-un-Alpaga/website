package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreArticleTransformer
import be.alpago.website.domain.Article
import io.ktor.server.application.Application

const val ARTICLE_TRANSFORMER = "articles"

fun Application.articles() {
    register<FirestoreAggregateTransformer<Article>>(ARTICLE_TRANSFORMER) {
        FirestoreArticleTransformer()
    }
}
