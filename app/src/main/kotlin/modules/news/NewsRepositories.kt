package be.alpago.website.modules.news

import be.alpago.website.libs.repository.FIRESTORE_PAGE_COLLECTION

object NewsRepositories {
    const val articles = "${FIRESTORE_PAGE_COLLECTION}/news/articles"
}
