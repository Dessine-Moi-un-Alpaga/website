package be.alpago.website.interfaces.modules

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreHighlightTransformer
import be.alpago.website.domain.Highlight
import be.alpago.website.register
import io.ktor.server.application.Application

const val HIGHLIGHT_TRANSFORMER = "highlights"

fun Application.highlightModule() {
    register<FirestoreAggregateTransformer<Highlight>>(HIGHLIGHT_TRANSFORMER) {
        FirestoreHighlightTransformer()
    }
}
