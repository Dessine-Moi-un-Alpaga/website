package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.head.stylesheet

import kotlinx.html.*

fun HEAD.fontAwesomeStylesheets() {
    link {
        attributes["crossorigin"] = "anonymous"
        attributes["referrerpolicy"] = "no-referrer"
        integrity = "sha512-P9vJUXK+LyvAzj8otTOKzdfF1F3UYVl13+F8Fof8/2QNb8Twd6Vb+VD52I7+87tex9UXxnzPgWA3rH96RExA7A=="
        href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/fontawesome.min.css"
        rel = LinkRel.stylesheet
    }
    link {
        attributes["crossorigin"] = "anonymous"
        attributes["referrerpolicy"] = "no-referrer"
        integrity = "sha512-tk4nGrLxft4l30r9ETuejLU0a3d7LwMzj0eXjzc16JQj+5U1IeVoCuGLObRDc3+eQMUcEQY1RIDPGvuA7SNQ2w=="
        href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/solid.min.css"
        rel = LinkRel.stylesheet
    }
    link {
        attributes["crossorigin"] = "anonymous"
        attributes["referrerpolicy"] = "no-referrer"
        integrity = "sha512-sVSECYdnRMezwuq5uAjKQJEcu2wybeAPjU4VJQ9pCRcCY4pIpIw4YMHIOQ0CypfwHRvdSPbH++dA3O4Hihm/LQ=="
        href = "https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/brands.min.css"
        rel = LinkRel.stylesheet
    }
}
