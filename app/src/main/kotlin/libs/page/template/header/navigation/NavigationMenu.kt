package be.alpago.website.libs.page.template.header.navigation

import be.alpago.website.libs.i18n.Messages
import be.alpago.website.libs.kotlin.i18n.capitalize
import be.alpago.website.libs.page.model.NavigationModel
import be.alpago.website.libs.page.model.PageModel
import be.alpago.website.libs.page.template.style.EscapeVelocity
import kotlinx.html.*

private val NavigationModel.Category.text
    get() = when (this) {
        NavigationModel.Category.DOG     -> capitalize(Messages.navigationMenuDogCategory)
        NavigationModel.Category.GELDING -> capitalize(Messages.navigationMenuGeldingCategory)
        NavigationModel.Category.MARE    -> capitalize(Messages.navigationMenuMareCategory)
        NavigationModel.Category.SOLD    -> capitalize(Messages.navigationMenuSoldCategory)
        NavigationModel.Category.STUD    -> capitalize(Messages.navigationMenuStudCategory)
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

            if (pageModel.navigationModel.animalsByCategory.isNotEmpty()) {
                li {
                    a {
                        +"${Messages.navigationMenuTitle}"
                    }
                    ul {
                        for (category in pageModel.navigationModel.animalsByCategory) {
                            li {
                                a {
                                    +category.key.text
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
