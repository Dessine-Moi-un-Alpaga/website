package be.alpago.website.libs.page.template.head.script

import be.alpago.website.libs.page.model.PageModel
import kotlinx.html.*

fun HEAD.photoswipe(pageModel: PageModel) {
    if (pageModel.hasPhotoGallery()) {
        script {
            type = "module"

            unsafe {
                +"""
                |
                |      import PhotoSwipeLightbox from '/webjars/photoswipe/dist/photoswipe-lightbox.esm.min.js';
                |      const lightbox = new PhotoSwipeLightbox({
                |        gallery: '#gallery',
                |        children: 'figure',
                |        pswpModule: () => import('/webjars/photoswipe/dist/photoswipe.esm.min.js')
                |      });
                |      lightbox.init();
                |    """.trimMargin()
            }
        }
    }
}
