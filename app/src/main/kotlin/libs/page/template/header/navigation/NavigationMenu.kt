package be.alpago.website.libs.page.template.header.navigation

import be.alpago.website.libs.environment.Environment
import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.i18n.capitalize
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
    Category.DOG -> capitalize(Messages.navigationMenuDogCategory)
    Category.GELDING -> capitalize(Messages.navigationMenuGeldingCategory)
    Category.MARE -> capitalize(Messages.navigationMenuMareCategory)
    Category.SOLD -> capitalize(Messages.navigationMenuSoldCategory)
    Category.STUD -> capitalize(Messages.navigationMenuStudCategory)
}

fun SECTION.navigationMenu(
    pageModel: PageModel,
) {
    nav {
        id = EscapeVelocity.nav

        ul {
            li {
                a {
                    href = "/"
                    +"${Messages.home}"
                }
            }
            li {
                a {
                    href = "/news.html"
                    +"${(Messages.news)}"
                }
            }

            if (pageModel.navigationModel.categories.isNotEmpty()) {
                li {
                    a {
                        +"${Messages.navigationMenuTitle}"
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
                    +"${Messages.factsheets}"
                }
            }
            li {
                a {
                    href = "/photos.html"
                    +"${Messages.photos}"
                }
            }
            li {
                a {
                    href = "#footer"
                    +"${Messages.contact}"
                }
            }
        }
    }
}
