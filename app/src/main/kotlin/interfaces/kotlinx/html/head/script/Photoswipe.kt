package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.script

import com.dessinemoiunalpaga.website.application.PageModel
import kotlinx.html.*

fun HEAD.photoswipe(pageModel: PageModel) {
    if (pageModel.hasPhotoGallery()) {
        script {
            type = "module"

            unsafe {
                +"""
                |
                |      import PhotoSwipeLightbox from '/assets/js/photoswipe/photoswipe-lightbox.esm.min.js';
                |      const lightbox = new PhotoSwipeLightbox({
                |        gallery: '#gallery',
                |        children: 'figure',
                |        pswpModule: () => import('/assets/js/photoswipe/photoswipe.esm.min.js')
                |      });
                |      lightbox.init();
                |    """.trimMargin()
            }
        }
    }
}
