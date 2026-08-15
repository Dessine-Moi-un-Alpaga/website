package com.dessinemoiunalpaga.website.domain

import kotlinx.serialization.Serializable

private const val MAX_SENDER_ADDRESS_LENGTH = 320
private const val MAX_MESSAGE_LENGTH = 5000
private const val MAX_SENDER_NAME_LENGTH = 100

@Serializable
data class Email(
    val message: String,
    val sender: Sender,
) {
    fun isValid() = sender.isValid()
            && message.length < MAX_MESSAGE_LENGTH

    @Serializable
    data class Sender(
        val address: String,
        val name: String,
    ) {
        fun isValid() = address.length < MAX_SENDER_ADDRESS_LENGTH
                && name.length < MAX_SENDER_NAME_LENGTH
    }

}
