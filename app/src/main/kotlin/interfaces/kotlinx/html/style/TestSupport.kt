package be.alpago.website.interfaces.kotlinx.html.style

import be.alpago.website.interfaces.kotlinx.html.TemplateProperties
import kotlinx.html.*

private const val TEST_ATTRIBUTE = "data-test-id"

fun Tag.testId(
    id: String,
    properties: TemplateProperties,
) {
    if (properties.includeTestIds) {
        attributes[TEST_ATTRIBUTE] = id
    }
}
