package com.dessinemoiunalpaga.website.interfaces.kotlinx.html.header.navigation

import com.dessinemoiunalpaga.website.application.NavigationModel
import com.dessinemoiunalpaga.website.application.PageModel
import com.dessinemoiunalpaga.website.domain.Animal
import com.dessinemoiunalpaga.website.i18n.Messages
import com.dessinemoiunalpaga.website.interfaces.kotlinx.html.style.EscapeVelocity
import com.dessinemoiunalpaga.website.libs.kotlin.i18n.capitalize
import kotlinx.html.*

private val NavigationModel.Category.text
    get() = when (this) {
        NavigationModel.Category.DOG     -> capitalize(Messages.navigationMenuDogCategory)
        NavigationModel.Category.GELDING -> capitalize(Messages.navigationMenuGeldingCategory)
        NavigationModel.Category.MARE    -> capitalize(Messages.navigationMenuMareCategory)
        NavigationModel.Category.SOLD    -> capitalize(Messages.navigationMenuSoldCategory)
        NavigationModel.Category.STUD    -> capitalize(Messages.navigationMenuStudCategory)
    }

typealias Category = Map.Entry<NavigationModel.Category, Set<Animal>>

private fun UL.category(category: Category) {
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

private fun UL.animals(pageModel: PageModel) {
    if (pageModel.navigationModel.animalsByCategory.isNotEmpty()) {
        li {
            a {
                +"${Messages.navigationMenuTitle}"
            }
            ul {
                for (category in pageModel.navigationModel.animalsByCategory) {
                    category(category)
                }
            }
        }
    }
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
            animals(pageModel)
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
