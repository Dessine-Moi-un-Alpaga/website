package be.alpago.website.modules.email

import kotlinx.serialization.Serializable

private const val MAX_FROM_LENGTH = 320
private const val MAX_MESSAGE_LENGTH = 5000
private const val MAX_NAME_LENGTH = 100

@Serializable
data class Email(
    val from: String,
    val name: String,
    val message: String,
) {
    fun isValid() = from.length <= MAX_FROM_LENGTH
            && name.length < MAX_NAME_LENGTH
            && message.length < MAX_MESSAGE_LENGTH
}
