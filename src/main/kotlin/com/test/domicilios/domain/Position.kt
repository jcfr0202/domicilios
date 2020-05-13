package com.test.domicilios.domain

import com.test.domicilios.exception.CustomException
import kotlin.math.abs

data class Position(
    val coordinateX: Int,
    val coordinateY: Int,
    val direction: Direction
) {

    companion object {
        private const val MAX_FORWARD_MOVEMENTS = 10
    }

    fun merge(nextPosition: Position): Position {
        val newCoordinateX = nextPosition.coordinateX + coordinateX
        val newCoordinateY = nextPosition.coordinateY + coordinateY
        if (abs(newCoordinateX) > MAX_FORWARD_MOVEMENTS || abs(newCoordinateY) > MAX_FORWARD_MOVEMENTS) {
            throw CustomException.invalidForwardMovements(MAX_FORWARD_MOVEMENTS)
        }
        return this.copy(coordinateX = newCoordinateX, coordinateY = newCoordinateY, direction = nextPosition.direction)
    }

    override fun toString() = "($coordinateX, $coordinateY) dirección $direction"
}