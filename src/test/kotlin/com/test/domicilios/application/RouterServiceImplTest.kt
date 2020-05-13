package com.test.domicilios.application

import com.test.domicilios.exception.CustomException
import com.test.domicilios.domain.Direction
import com.test.domicilios.domain.Position
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.File

internal class RouterServiceImplTest {

    private val deliveryRoutesFilesPath = this.javaClass.getResource("/delivery-routes-files").path

    @Nested
    inner class SuccessScenarios {

        @Test
        fun `drone delivers 3 packages successfully`() {
            val routesFilePath = File("$deliveryRoutesFilesPath/in01.txt").absolutePath
            val droneCapacity = 3
            val initialPosition = Position(0, 0, Direction.Vertical.North)
            val actualTrace: List<Position> =
                RouterServiceImpl.launchDrone(routesFilePath, droneCapacity, initialPosition)

            val expectedTrace =
                listOf(
                    Position(-3, -2, Direction.Horizontal.West),
                    Position(-2, -1, Direction.Horizontal.East),
                    Position(2, 1, Direction.Horizontal.East)
                )
            assertThat(actualTrace, Is(equalTo(expectedTrace)))
        }
    }

    @Nested
    inner class FailedScenarios {

        @Test
        fun `giving a route that makes the drone shift more than 10 blocks`() {
            val routesFilePath = File("$deliveryRoutesFilesPath/in99.txt").absolutePath
            val droneCapacity = 2
            val initialPosition = Position(0, 0, Direction.Vertical.North)

            val actualException = Assertions.assertThrows(CustomException::class.java) {
                RouterServiceImpl.launchDrone(routesFilePath, droneCapacity, initialPosition)
            }
            assertThat(actualException.message,
                Is(equalTo("Drone is not capable of going further than 10 blocks around.")))
        }

    }
}