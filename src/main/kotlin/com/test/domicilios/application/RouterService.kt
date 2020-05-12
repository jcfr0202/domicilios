package com.test.domicilios.application

import com.test.domicilios.dto.Route
import com.test.domicilios.domain.Position

interface RouteService {
    fun followPath(route: Route, position: Position): List<Position>
}

object RouteServiceImpl : RouteService {

    override fun followPath(route: Route, position: Position): List<Position> {
        val trace: MutableList<Position> = mutableListOf()
        route.toEntity().fold(position) { currentPosition, movement ->
            currentPosition
                .merge(nextPosition = movement.move(position = Position(0, 0, currentPosition.direction)))
                .also { trace.add(it) }
        }
        return trace
    }
}