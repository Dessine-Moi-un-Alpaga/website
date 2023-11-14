package be.alpago.website.libs.page.template.head.stylesheet

import be.alpago.website.libs.page.model.PageModel
import kotlinx.html.*

fun HEAD.photoswipeStylesheet(pageModel: PageModel) {
    if (pageModel.hasPhotoGallery()) {
        link {
            rel = LinkRel.stylesheet
            href = "/webjars/photoswipe/dist/photoswipe.css"
        }
    }
}
