package com.test.domicilios.domain

import com.test.domicilios.exception.CustomException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import java.util.stream.Stream

internal class PositionTest {

    @TestFactory
    fun `success scenarios`(): Stream<DynamicTest> =
        Stream.of(
            Position(5, 1, Direction.Vertical.North),
            Position(7, 3, Direction.Vertical.North),
            Position(4, 2, Direction.Vertical.North)
        )
        .map { input ->
            val initialPosition = Position(3, 6, Direction.Vertical.North)
            DynamicTest.dynamicTest("Accepted: $input") {
                assertDoesNotThrow { initialPosition.merge(input) }
            }
        }

    @TestFactory
    fun `Route implies having to travel more than 10 blocks`(): Stream<DynamicTest> =
        Stream.of(
                Position(8, 1, Direction.Vertical.North),
                Position(7, 6, Direction.Vertical.North)
            )
            .map { input ->
                val initialPosition = Position(3, 6, Direction.Vertical.North)
                val expectedMessage = "Drone is not capable of going further than 10 blocks around."
                DynamicTest.dynamicTest("Rejected: $input") {
                    assertThrows<CustomException> { initialPosition.merge(input) }
                        .also { actualException ->
                            assertThat(actualException.message, Is(equalTo(expectedMessage)))
                        }
                }
            }
}