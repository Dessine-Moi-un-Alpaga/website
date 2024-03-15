package be.alpago.website.interfaces.ktor

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreHighlightTransformer
import be.alpago.website.domain.Highlight
import io.ktor.server.application.Application

const val HIGHLIGHT_TRANSFORMER = "highlights"

fun Application.highlights() {
    register<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER) {
        FirestoreHighlightTransformer()
    }
}
