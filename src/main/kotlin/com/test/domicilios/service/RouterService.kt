package com.test.domicilios.service

import com.test.domicilios.model.Path
import com.test.domicilios.model.Position
import com.test.domicilios.model.direction.Direction
import com.test.domicilios.exception.CustomException
import com.test.domicilios.model.movement.Movement
import com.test.domicilios.model.movement.Movements
import kotlin.math.abs

interface RouteService {
    fun followPath(path: Path, position: Position): List<Position>
}

object RouteServiceImpl : RouteService {

    private const val MAX_FORWARD_MOVEMENTS = 10

    private val droneDistanceCapacityIsViolated: ((Int, Int) -> Boolean) = { newPositionX, newPositionY ->
        abs(newPositionX) > MAX_FORWARD_MOVEMENTS || abs(newPositionY) > MAX_FORWARD_MOVEMENTS
    }

    override fun followPath(path: Path, position: Position): List<Position> {
        var position = position
        val trace: MutableList<Position> = ArrayList()
        val pathString: String = path.path
        for (currentMovement in pathString.toCharArray()) {
            var currentPosition = processMovement(currentMovement, Position(0, 0, position.direction))
            currentPosition = mergePositions(position, currentPosition)
            position = currentPosition
            trace.add(position)
        }
        return trace
    }

    private fun mergePositions(previousPosition: Position, currentPosition: Position): Position {
        val newPositionX: Int = currentPosition.x + previousPosition.x
        val newPositionY: Int = currentPosition.y + previousPosition.y
        if (droneDistanceCapacityIsViolated.invoke(newPositionX, newPositionY)) {
            throw CustomException.invalidForwardMovements(MAX_FORWARD_MOVEMENTS)
        }
        return Position(newPositionX, newPositionY, currentPosition.direction)
    }

    private fun processMovement(initialOfMovement: Char, position: Position): Position {
        val movement: Movement = Movements.getMovement(initialOfMovement)
        val direction: Direction = position.direction
        return movement.move(direction, position)
    }
}