package com.dessinemoiunalpaga.website.adapters.email.jakarta.mail

data class JakartaMailProperties(
    /**
     * The sender email address used by the SMTP server.
     */
    val address: String,

    /**
     * The hostname of the SMTP server.
     */
    val smtpServerAddress: String,

    /**
     * The password to authenticate with the SMTP server.
     */
    val smtpServerPassword: String,

    /**
     * The port the SMTP server uses to listen for connections.
     */
    val smtpServerPort : Int,

    /**
     * The username to authenticate with the SMTP server.
     */
    val smtpServerUsername: String,
)
