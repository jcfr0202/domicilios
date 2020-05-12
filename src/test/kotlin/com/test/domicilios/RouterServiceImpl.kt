package com.test.domicilios

import com.test.domicilios.dto.Route
import com.test.domicilios.exception.CustomException
import com.test.domicilios.model.Direction
import com.test.domicilios.model.Position
import com.test.domicilios.service.RouteServiceImpl
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.hamcrest.CoreMatchers.`is` as Is

internal class RouterServiceImpl {

    @Nested
    inner class HappyPaths {

        @Test
        fun `starting from leftward rotation`() {
            val route = Route.createNew("IAAAIAAD")
            val position = Position(0, 0, Direction.Vertical.North)
            val actualTrace: List<Position> = RouteServiceImpl.followPath(route, position)

            val expectedTrace = listOf (
                Position(0, 0, Direction.Horizontal.West),
                Position(-1, 0, Direction.Horizontal.West),
                Position(-2, 0, Direction.Horizontal.West),
                Position(-3, 0, Direction.Horizontal.West),
                Position(-3, 0, Direction.Vertical.South),
                Position(-3, -1, Direction.Vertical.South),
                Position(-3, -2, Direction.Vertical.South),
                Position(-3, -2, Direction.Horizontal.West)
            )
            assertThat(actualTrace, Is(equalTo(expectedTrace)))
        }

        @Test
        fun `starting from rightward rotation`() {
            val route = Route.createNew("DDAIAD")
            val position = Position(-2, 4, Direction.Vertical.North)
            val actualTrace: List<Position> = RouteServiceImpl.followPath(route, position)

            val expectedTrace = listOf (
                Position(-2, 4, Direction.Horizontal.East),
                Position(-2, 4, Direction.Vertical.South),
                Position(-2, 3, Direction.Vertical.South),
                Position(-2, 3, Direction.Horizontal.East),
                Position(-1, 3, Direction.Horizontal.East),
                Position(-1, 3, Direction.Vertical.South)
            )
            assertThat(actualTrace, Is(equalTo(expectedTrace)))
        }

        @Test
        fun `starting from forward shift`() {
            val route = Route.createNew("AAAAIIIIIAADDDDD")
            val position = Position(0, 0, Direction.Vertical.North)
            val actualTrace: List<Position> = RouteServiceImpl.followPath(route, position)

            val expectedTrace = listOf (
                Position(0, 1, Direction.Vertical.North),
                Position(0, 2, Direction.Vertical.North),
                Position(0, 3, Direction.Vertical.North),
                Position(0, 4, Direction.Vertical.North),
                Position(0, 4, Direction.Horizontal.West),
                Position(0, 4, Direction.Vertical.South),
                Position(0, 4, Direction.Horizontal.East),
                Position(0, 4, Direction.Vertical.North),
                Position(0, 4, Direction.Horizontal.West),
                Position(-1, 4, Direction.Horizontal.West),
                Position(-2, 4, Direction.Horizontal.West),
                Position(-2, 4, Direction.Vertical.North),
                Position(-2, 4, Direction.Horizontal.East),
                Position(-2, 4, Direction.Vertical.South),
                Position(-2, 4, Direction.Horizontal.West),
                Position(-2, 4, Direction.Vertical.North)
            )

            assertThat(actualTrace, Is(equalTo(expectedTrace)))
        }

    }

    @Nested
    inner class FailedScenarios {

        @Test
        fun `receiving a route that makes the drone shift more than 10 blocks`() {
            val route = Route.createNew("AAAAIIIIIAAAAAAAAAAAADADADAAAAAAAAAA")
            val position = Position(0, 0, Direction.Vertical.North)

            val actualException = Assertions.assertThrows(CustomException::class.java) {
                RouteServiceImpl.followPath(route, position)
            }
            assertThat(actualException.message,
                Is(equalTo("Drone is not capable of going further than 10 blocks around.")))
        }
    }

}