# Package com.dessinemoiunalpaga.website.adapters

The layer that contains the implementations of the secondary (driven) ports.

# Package com.dessinemoiunalpaga.website.adapters.persistence.firestore

[Google Cloud Firestore](https://cloud.google.com/products/firestore) implementations of the persistence-related ports.

# Package com.dessinemoiunalpaga.website.adapters.email.jakarta.mail

[Jakarta Mail](https://jakartaee.github.io/mail-api/) implementation of the email-sending port.

# Package com.dessinemoiunalpaga.website.application

The application layer, mostly responsible for modelling the structure of the website pages and populating that model
with the persisted contents.

# Package com.dessinemoiunalpaga.website.application.queries

Implementations of the use cases dedicated to displaying web pages.

# Package com.dessinemoiunalpaga.website.application.usecases

Names of the functionalities offered to an external user. In a nutshell: showing web pages and managing their content,
as well as sending emails.

# Package com.dessinemoiunalpaga.website.domain

The core domain of the application.

This application being mostly CRUDs to configure the contents of a static website, the core domain can be described as
anemic.

# Package com.dessinemoiunalpaga.website.interfaces

The layer that contains the implementations of the primary (driving) ports.

# Package com.dessinemoiunalpaga.website.interfaces.kotlinx.html

[kotlinx.html](https://github.com/Kotlin/kotlinx.html) implementation of the website templates.

# Package com.dessinemoiunalpaga.website.interfaces.ktor.routes

HTTP endpoints exposed by the application.

# Package com.dessinemoiunalpaga.website.libs

Utility code that could be extracted to their own modules.

# Package com.dessinemoiunalpaga.website.libs.adapters.persistence

Utility code used by persistence-related adapters.

# Package com.dessinemoiunalpaga.website.libs.domain

Base domain abstractions.

# Package com.dessinemoiunalpaga.website.libs.domain.ports.persistence

Persistence-related ports.

# Package com.dessinemoiunalpaga.website.libs.i18n4k

Utility code for configuring the [i18n4k](https://comahe-de.github.io/i18n4k/) library.

# Package com.dessinemoiunalpaga.website.libs.kotlin.i18n

Extensions of the kotlin standard library related to internationalization.

# Package com.dessinemoiunalpaga.website.libs.kotlin.retry

Simple implementation of a retry functionality for a given block of code.

# Package com.dessinemoiunalpaga.website.libs.kotlin.serialization

Extensions of the kotlin serialization libraries.

# Package com.dessinemoiunalpaga.website.libs.ktor.plugins.webjars

Native-friendly replacement for the ktor Webjars plugin.

# Package com.dessinemoiunalpaga.website.libs.ktor.routes

Ktor routes for managing persistent AggregateRoots.

# Package com.dessinemoiunalpaga.website.libs.slf4j

Utility code for configuring logging using SLF4J.
