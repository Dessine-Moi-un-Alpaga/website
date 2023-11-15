package be.alpago.website.interfaces.koin

import be.alpago.website.adapters.firestore.FirestoreAggregateTransformer
import be.alpago.website.adapters.firestore.FirestoreHighlightTransformer
import be.alpago.website.domain.Highlight
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val HIGHLIGHT_TRANSFORMER = "highlights"

fun Application.highlightModule() {
    koin {
        modules(
            module {
                single<FirestoreAggregateTransformer<Highlight>>(
                    named(HIGHLIGHT_TRANSFORMER)
                ) {
                    FirestoreHighlightTransformer()
                }
            }
        )
    }
}
