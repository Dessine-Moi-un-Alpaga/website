/*
 * Dessine-Moi un Alpaga's website
 * Copyright (c) 2026 Alpago SRL
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package be.alpago.website.adapters.email.jakarta.mail

import be.alpago.website.domain.Email
import de.comahe.i18n4k.Locale
import de.comahe.i18n4k.config.I18n4kConfigImmutable
import de.comahe.i18n4k.i18n4k
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
        i18n4k = I18n4kConfigImmutable().withLocale(Locale.FRENCH)
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
        messageSlot.captured.subject.shouldBe("[website] Message reçu de ${email.name} (${email.from})")
        messageSlot.captured.content.shouldBe(email.message)
    }
}