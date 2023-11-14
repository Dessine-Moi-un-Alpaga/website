package be.alpago.website.modules.article

import be.alpago.website.domain.highlight.FirestoreHighlightTransformer
import be.alpago.website.domain.highlight.Highlight
import be.alpago.website.libs.firestore.FirestoreAggregateTransformer
import io.ktor.server.application.Application
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ktor.plugin.koin

const val HIGHLIGHT_TRANSFORMER = "highlights"

private fun highlightModule() = module {
    single<FirestoreAggregateTransformer<Highlight>>(
        named(HIGHLIGHT_TRANSFORMER)
    ) {
        FirestoreHighlightTransformer()
    }
}

fun Application.highlights() {
    koin {
        modules(highlightModule())
    }
}
