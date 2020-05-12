package com.test.domicilios

import com.test.domicilios.model.Directions
import com.test.domicilios.model.Path
import com.test.domicilios.model.Position
import com.test.domicilios.model.direction.North
import com.test.domicilios.model.direction.West
import com.test.domicilios.service.RouteService
import com.test.domicilios.service.RouteServiceImpl
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Test

internal class RouterServiceImpl {

    @Test
    fun path1() {
        val path = Path("AAAAIAAD")
        val position = Position(0, 0, North())
        val routeService: RouteService = RouteServiceImpl
        val actualTrace: List<Position> = routeService.followPath(path, position)

        val expectedTrace = listOf (
            Position(0, 1, North()),
            Position(0, 2, North()),
            Position(0, 3, North()),
            Position(0, 4, North()),
            Position(0, 4, West()),
            Position(-1, 4, West()),
            Position(-2, 4, West()),
            Position(-2, 4, North())
        )
        assertThat(actualTrace, Is(equalTo(expectedTrace)))
    }

}