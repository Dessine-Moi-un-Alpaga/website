package be.alpago.website.interfaces.kotlinx.html.body

import be.alpago.website.application.AnimalSectionModel
import be.alpago.website.domain.Animal
import be.alpago.website.interfaces.kotlinx.html.Messages
import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import be.alpago.website.interfaces.kotlinx.html.style.EscapeVelocity
import be.alpago.website.libs.kotlin.i18n.capitalize
import io.ktor.server.html.Template
import io.ktor.server.html.insert
import kotlinx.html.*
import java.time.LocalDate.now
import java.time.Period
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

private fun Animal.Sex.text() = when (this) {
    Animal.Sex.FEMALE -> "${Messages.female}"
    Animal.Sex.MALE   -> "${Messages.male}"
}

private fun Animal.age(): String {
    val age = Period.between(dateOfBirth, now())
    val ageInYears = age.years

    return if (ageInYears < 2) {
        Messages.ageInMonths(age.months, Locale.FRENCH)
    } else {
        Messages.ageInYears(age.years, Locale.FRENCH)
    }
}

private fun Animal.formattedDateOfBirth() = dateOfBirth.format(
    DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
        .withLocale(Locale.FRENCH)
)

private fun Animal.Color.text() = when (this) {
    Animal.Color.BAY_BLACK   -> "${Messages.bayBlack}"
    Animal.Color.MEDIUM_FAWN -> "${Messages.mediumFawn}"
    Animal.Color.DARK_FAWN   -> "${Messages.darkFawn}"
    Animal.Color.WHITE       -> "${Messages.white}"
}

private fun LI.reference(reference: Animal.Reference) {
    if (reference.link == null) {
        span {
            +reference.name
        }
    } else {
        a {
            href = reference.link

            span {
                +reference.name
            }
        }
    }
}

class AnimalSectionTemplate(
    private val properties: TemplateProperties,
    private val model: AnimalSectionModel,
) : Template<FlowContent> {

    override fun FlowContent.apply() {
        insert(SectionTemplate(model)) {
            pageTitle {
                +model.sectionTitle
            }
            content {
                div {
                    classes = setOf(
                        EscapeVelocity.row,
                        EscapeVelocity.gtr150,
                    )

                    div {
                        classes = setOf(
                            EscapeVelocity.col4,
                            EscapeVelocity.col12Medium,
                        )

                        section {
                            classes = setOf(
                                EscapeVelocity.box
                            )

                            header {
                                h2 {
                                    +"${capitalize(Messages.about)} "

                                    span {
                                        +model.animal.name
                                    }
                                }

                                a {
                                    classes = setOf(
                                        EscapeVelocity.image
                                    )

                                    img {
                                        alt = model.animal.thumbnailDescription
                                        src = "${properties.baseAssetUrl}/images/animals/${model.animal.id}_thumb.png"
                                    }
                                }

                                ul {
                                    classes = setOf(
                                        EscapeVelocity.style3
                                    )

                                    li {
                                        b {
                                            +"${capitalize(Messages.name)} : "
                                        }
                                        span {
                                            +model.animal.fullName
                                        }
                                    }

                                    li {
                                        b {
                                            +"${capitalize(Messages.sex)} : "
                                        }
                                        span {
                                            +model.animal.sex.text()
                                        }
                                    }

                                    li {
                                        b {
                                            +"${capitalize(Messages.age)} : "
                                        }

                                        span {
                                            +model.animal.age()
                                        }
                                    }

                                    li {
                                        b {
                                            +"${capitalize(Messages.birthDate)} : "
                                        }

                                        span {
                                            +model.animal.formattedDateOfBirth()
                                        }
                                    }

                                    li {
                                        b {
                                            +"${capitalize(Messages.color)} : "
                                        }

                                        span {
                                            +model.animal.color.text()
                                        }
                                    }

                                    li {
                                        b {
                                            +"${capitalize(Messages.dam)} : "
                                        }

                                        reference(model.animal.dam)
                                    }

                                    li {
                                        b {
                                            +"${capitalize(Messages.sire)} : "
                                        }

                                        reference(model.animal.sire)
                                    }
                                }
                            }
                        }

                        if (model.fiberAnalyses.isNotEmpty()) {
                            section {
                                classes = setOf(
                                    EscapeVelocity.box
                                )

                                header {
                                    h2 {
                                        +capitalize(Messages.fiberAnalyses)
                                    }
                                }

                                table {
                                    classes = setOf(
                                        EscapeVelocity.default
                                    )

                                    tr {
                                        th { }
                                        th { +"${Messages.averageFiberDiamterAbbreviation}" }
                                        th { +"${Messages.standardDeviationAbbreviation}" }
                                        th { +"${Messages.coefficientOfVariationAbbreviation}" }
                                        th { +"${Messages.comfortFactorAbbreviation}" }
                                    }

                                    for (fibreAnalysis in model.fiberAnalyses) {
                                        tr {
                                            td {
                                                b {
                                                    span {
                                                        +"${fibreAnalysis.year}"
                                                    }
                                                }
                                            }
                                            td { +fibreAnalysis.averageFiberDiameter }
                                            td { +fibreAnalysis.standardDeviation }
                                            td { +fibreAnalysis.coefficientOfVariation }
                                            td { +fibreAnalysis.comfortFactor }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    div {
                        classes = setOf(
                            EscapeVelocity.col8,
                            EscapeVelocity.col12Medium,
                            EscapeVelocity.impMedium,
                        )

                        div {
                            id = EscapeVelocity.content

                            article {
                                classes = setOf(
                                    EscapeVelocity.box,
                                    EscapeVelocity.post,
                                )

                                header {
                                    classes = setOf(
                                        EscapeVelocity.style1
                                    )

                                    h2 {
                                        unsafe {
                                            +model.animal.title
                                        }
                                    }
                                    p {
                                        unsafe {
                                            +model.animal.subtitle
                                        }
                                    }
                                }
                                a {
                                    classes = setOf(
                                        EscapeVelocity.image,
                                        EscapeVelocity.featured,
                                    )

                                    img {
                                        alt = model.animal.bannerDescription
                                        src = "${properties.baseAssetUrl}/images/animals/${model.animal.id}_banner.png"
                                    }
                                }
                                unsafe {
                                    +model.animal.text
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
