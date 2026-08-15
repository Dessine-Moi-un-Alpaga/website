package com.dessinemoiunalpaga.website.adapters.email.jakarta.mail

import com.dessinemoiunalpaga.website.domain.Email
import io.github.serpro69.kfaker.Faker
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockkStatic
import io.mockk.slot
import jakarta.mail.Message
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class JakartaMailServiceTest {

    @Test
    fun testEmail() = runTest {
        val faker = Faker()
        val email: Email = faker.randomProvider.randomClassInstance()
        val properties = faker.randomProvider.randomClassInstance<JakartaMailProperties> {
            namedParameterGenerator("address") {
                faker.internet.email()
            }
            namedParameterGenerator("smtpServerUsername") {
                faker.internet.email()
            }
        }
        val service = JakartaMailService(properties)
        val messageSlot = slot<Message>()
        mockkStatic(Transport::class)
        every {
            Transport.send(
                capture(messageSlot),
                properties.smtpServerUsername,
                properties.smtpServerPassword,
            )
        } just Runs
        service.send(email)
        messageSlot.isCaptured.shouldBeTrue()
        messageSlot.captured.session.properties["mail.smtp.auth"].shouldBe(true)
        messageSlot.captured.session.properties["mail.smtp.starttls.enable"].shouldBe(true)
        messageSlot.captured.session.properties["mail.smtp.host"].shouldBe(properties.smtpServerAddress)
        messageSlot.captured.session.properties["mail.smtp.port"].shouldBe(properties.smtpServerPort)
        messageSlot.captured.from.shouldContainAll(*InternetAddress.parse(properties.smtpServerUsername))
        messageSlot.captured.getRecipients(Message.RecipientType.TO)
            .shouldContainAll(*InternetAddress.parse(properties.address))
        messageSlot.captured.subject.shouldBe("[website] Message reçu de ${email.sender.name} (${email.sender.address})")
        messageSlot.captured.content.shouldBe(email.message)
    }
}
