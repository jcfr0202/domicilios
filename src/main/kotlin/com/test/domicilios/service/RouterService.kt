package com.test.domicilios.service

import com.test.domicilios.dto.Route
import com.test.domicilios.model.Position
import com.test.domicilios.model.Movement

interface RouteService {
    fun followPath(route: Route, position: Position): List<Position>
}

object RouteServiceImpl : RouteService {

    override fun followPath(route: Route, position: Position): List<Position> {
        val trace: MutableList<Position> = mutableListOf()
        route.value.toCharArray().fold(position) { currentPosition, movement ->
            currentPosition
                .merge(nextPosition = Movement.getMovement(movement).move(Position(0, 0, currentPosition.direction)))
                .also { trace.add(it) }
        }
        return trace
    }
}