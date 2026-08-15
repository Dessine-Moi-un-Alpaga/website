package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import com.dessinemoiunalpaga.website.application.PageModel
import kotlinx.html.*

fun HEAD.photoswipeStylesheet(pageModel: PageModel) {
    if (pageModel.hasPhotoGallery()) {
        link {
            rel = LinkRel.stylesheet
            href = "/webjars/photoswipe/dist/photoswipe.css"
        }
    }
}
