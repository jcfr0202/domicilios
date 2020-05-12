package com.test.domicilios.dto

import org.apache.commons.lang3.StringUtils
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.util.stream.Stream

internal class RouteTest {

    @TestFactory
    fun `success scenarios`(): Stream<DynamicTest> =
        Stream.of(
                "AAAAIIDDAIDDIADIADDIIDA",
                "IAAADADAIIAAiaiaiaiaddd",
                "aaidaiiia"
            )
            .map { input ->
                dynamicTest("Accepted: $input") { assertDoesNotThrow { Route.createNew(input) } }
            }

    @TestFactory
    fun `Route string does not match regex pattern`(): Stream<DynamicTest> =
        Stream.of(
                "12AAiaia",
                "IAAAD ADAIIAAiaiaiaiaddd",
                "aai?daiiia@%&AAD&$|idaiad"
            )
            .map { input ->
                dynamicTest("Rejected: $input") {
                    val expectedMessage = "Route only accepts the following alphabetic characters: [A, D, I]. Route: '$input'"
                    assertThrows<IllegalArgumentException> { Route.createNew(input) }
                        .also { actualException ->
                            assertThat(actualException.message, Is(equalTo(expectedMessage)))
                        }
                }
            }

    @TestFactory
    fun `Route string does not have the right length`(): Stream<DynamicTest> =
        Stream.of(
                "",
                "ai",
                "aid",
                "aAID",
                StringUtils.repeat("A", 201)
            )
            .map { input ->
                dynamicTest("Rejected: $input") {
                    val expectedMessage = "Route string must have between 5 and 200 characters. Route: '$input'"
                    assertThrows<IllegalArgumentException> { Route.createNew(input) }
                        .also { actualException ->
                            assertThat(actualException.message, Is(equalTo(expectedMessage)))
                        }
                }
            }

}
