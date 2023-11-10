package be.alpago.website.modules.factsheet

import be.alpago.website.libs.repository.FIRESTORE_PAGE_COLLECTION

object FirestoreFactsheetRepositories {

    const val article = "${FIRESTORE_PAGE_COLLECTION}/factsheets/article"
    const val highlights = "${FIRESTORE_PAGE_COLLECTION}/factsheets/highlights"
}
