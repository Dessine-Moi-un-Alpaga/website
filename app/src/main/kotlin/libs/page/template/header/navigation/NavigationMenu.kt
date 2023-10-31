package be.alpago.website.libs.page.template.header.navigation

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.page.model.Category
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.SECTION
import kotlinx.html.a
import kotlinx.html.id
import kotlinx.html.li
import kotlinx.html.nav
import kotlinx.html.ul

private fun Category.text() = when (this) {
    Category.DOG -> "La garde hongroise"
    Category.GELDING -> "Mâles castrés"
    Category.MARE -> "Femelles"
    Category.SOLD -> "Vendus"
    Category.STUD -> "Étalons"
}

fun SECTION.navigationMenu(
    environment: Environment,
    pageModel: PageModel,
) {
    nav {
        id = EscapeVelocity.nav

        ul {
            li {
                a {
                    href = "/"
                    +"Accueil"
                }
            }
            li {
                a {
                    href = "/news.html"
                    +"Actualités"
                }
            }

            if (pageModel.navigationModel.categories.isNotEmpty()) {
                li {
                    a {
                        +"Nos animaux"
                    }
                    ul {
                        for (category in pageModel.navigationModel.categories) {
                            li {
                                a {
                                    +category.key.text()
                                }
                                ul {
                                    for (animal in category.value) {
                                        li {
                                            a {
                                                href = "/animals/${animal.id}.html"
                                                +animal.name
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            li {
                a {
                    href = "/factsheets.html"
                    +"Fiches Pratiques"
                }
            }
            li {
                a {
                    href = "/photos.html"
                    +"Photos"
                }
            }
            li {
                a {
                    href = "#footer"
                    +"Contact"
                }
            }
        }
    }
}
