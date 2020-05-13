package com.test.domicilios.domain

import com.test.domicilios.exception.CustomException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException
import java.util.stream.Stream

internal class DroneTest {

    private val deliveryRoutesFilesPath = this.javaClass.getResource("/delivery-routes-files").path

    @TestFactory
    fun `success scenarios`(): Stream<DynamicTest> =
        Stream.of(
            Pair("$deliveryRoutesFilesPath/in01.txt", 3),
            Pair("$deliveryRoutesFilesPath/in02.txt", 3),
            Pair("$deliveryRoutesFilesPath/in03.txt", 3),
            Pair("$deliveryRoutesFilesPath/in04.txt", 3),
            Pair("$deliveryRoutesFilesPath/in11.txt", 3),
            Pair("$deliveryRoutesFilesPath/in12.txt", 3),
            Pair("$deliveryRoutesFilesPath/in20.txt", 3),
            Pair("$deliveryRoutesFilesPath/in99.txt", 2)
        )
        .map { input ->
            DynamicTest.dynamicTest("Accepted: $input") {
                assertDoesNotThrow { Drone(input.first, input.second) }
            }
        }

    @Nested
    inner class FailedScenarios {

        @Test
        fun `routes file is empty`() {
            val routesFilePath = "$deliveryRoutesFilesPath/in97.txt"
            val expectedMessage = "File '$routesFilePath' is empty."
            assertThrows<IllegalArgumentException> { Drone(routesFilePath, 3) }
                .also { actualException ->
                    assertThat(actualException.message, Is(equalTo(expectedMessage)))
                }
        }

        @Test
        fun `routes file size is too big`() {
            val routesFilePath = "$deliveryRoutesFilesPath/in98.txt"
            val expectedMessage = "File '$routesFilePath' is more than 1024 bytes (1 KB) size, try with a smaller one."
            assertThrows<IllegalArgumentException> { Drone(routesFilePath, 3) }
                .also { actualException ->
                    assertThat(actualException.message, Is(equalTo(expectedMessage)))
                }
        }

        @Test
        fun `drone capacity specified is different of number of routes in file`() {
            val droneCapacity = 2
            val expectedMessage = "Drone capacity specified (capacity=$droneCapacity) is not the same as the " +
                "number of routes specified in the input file"
            assertThrows<CustomException> { Drone("$deliveryRoutesFilesPath/in01.txt", droneCapacity) }
                .also { actualException ->
                    assertThat(actualException.message, Is(equalTo(expectedMessage)))
                }
        }

        @TestFactory
        fun `invalid routes files names`(): Stream<DynamicTest> =
            Stream.of(
                Pair("$deliveryRoutesFilesPath/un01.txt", 3),
                Pair("$deliveryRoutesFilesPath/ino01.txt", 3),
                Pair("$deliveryRoutesFilesPath/in011.txt", 3),
                Pair("$deliveryRoutesFilesPath/iin01.txt", 3),
                Pair("$deliveryRoutesFilesPath/in01txt", 3),
                Pair("$deliveryRoutesFilesPath/in01.xt", 3),
                Pair("$deliveryRoutesFilesPath/01.txt", 3),
                Pair("$deliveryRoutesFilesPath/in.txt", 3),
                Pair("$deliveryRoutesFilesPath/in1.txt", 3),
                Pair("$deliveryRoutesFilesPath/1.txt", 3)
            )
            .map { input ->
                val expectedMessage = "File '${input.first}' does not have the correct name format: " +
                    "in{number from 00 to 99}.txt, i.e. 'in01.txt'."
                DynamicTest.dynamicTest("Rejected: $input") {
                    assertThrows<IllegalArgumentException> { Drone(input.first, input.second) }
                        .also { actualException ->
                            assertThat(actualException.message, Is(equalTo(expectedMessage)))
                        }
                }
            }
    }
}