package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import com.dessinemoiunalpaga.website.application.PageModel
import kotlinx.html.*

fun HEAD.photoswipeStylesheet(pageModel: PageModel) {
    if (pageModel.hasPhotoGallery()) {
        link {
            externalStylesheetAttributes()
            href = "https://cdnjs.cloudflare.com/ajax/libs/photoswipe/5.4.4/photoswipe.min.css"
            integrity = "sha512-LFWtdAXHQuwUGH9cImO9blA3a3GfQNkpF2uRlhaOpSbDevNyK1rmAjs13mtpjvWyi+flP7zYWboqY+8Mkd42xA=="
            rel = LinkRel.stylesheet
        }
    }
}
