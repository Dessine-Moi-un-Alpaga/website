package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.body

import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.id

fun DIV.spinnerModal() {
    div {
        classes = setOf("modal")
        id = "modal"
    }
}
