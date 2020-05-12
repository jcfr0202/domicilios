package com.test.domicilios.domain

import com.test.domicilios.exception.CustomException
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.assertThrows
import java.util.stream.Stream

internal class MovementTest {

    @Nested
    inner class ValidMovements {

        @Test
        fun `left ward`() {
            val position = Position(0, 0, Direction.Vertical.North)
            val actualPosition = Movement.Rotation.LeftWard.move(position)
            val expectedPosition = Position(0, 0, Direction.Horizontal.West)
            assertThat(actualPosition, Is(equalTo(expectedPosition)))
        }

        @Test
        fun `right ward`() {
            val position = Position(0, 0, Direction.Vertical.North)
            val actualPosition = Movement.Rotation.RightWard.move(position)
            val expectedPosition = Position(0, 0, Direction.Horizontal.East)
            assertThat(actualPosition, Is(equalTo(expectedPosition)))
        }

        @Test
        fun forward() {
            val position = Position(0, 0, Direction.Vertical.North)
            val actualPosition = Movement.Forward.move(position)
            val expectedPosition = Position(0, 1, Direction.Vertical.North)
            assertThat(actualPosition, Is(equalTo(expectedPosition)))
        }

    }

    @TestFactory
    fun `valid movement initials`(): Stream<DynamicTest> =
        Stream.of('A', 'I', 'D')
            .map { input ->
                DynamicTest.dynamicTest("Accepted: $input") { Assertions.assertDoesNotThrow { Movement.getMovement(input) } }
            }

    @TestFactory
    fun `invalid movement initials`(): Stream<DynamicTest> =
        "bcefghjklmnopqrstuvwxyz".toUpperCase().chars().mapToObj { it.toChar() }
            .map { input ->
                DynamicTest.dynamicTest("Rejected: $input") {
                    val expectedMessage = "Movement with initial='$input' has not been recognized"
                    assertThrows<CustomException> { Movement.getMovement(input) }
                        .also { actualException ->
                            assertThat(actualException.message, Is(equalTo(expectedMessage)))
                        }
                }
            }
}