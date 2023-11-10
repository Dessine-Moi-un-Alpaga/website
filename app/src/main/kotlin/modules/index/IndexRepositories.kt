package be.alpago.website.modules.index

import be.alpago.website.libs.repository.FIRESTORE_PAGE_COLLECTION

object IndexRepositories {

    const val article = "${FIRESTORE_PAGE_COLLECTION}/index/article"
    const val guilds = "${FIRESTORE_PAGE_COLLECTION}/index/guilds"
    const val news = "${FIRESTORE_PAGE_COLLECTION}/index/news"
    const val trainings = "${FIRESTORE_PAGE_COLLECTION}/index/trainings"
}
