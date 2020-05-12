package com.test.domicilios.model.movement

import com.test.domicilios.model.Directions
import com.test.domicilios.model.Position
import com.test.domicilios.model.direction.Direction

class Left : Movement {

    companion object {
        const val orientation = -1
    }

    override fun move(direction: Direction, position: Position): Position {
        var directionNumber = direction.getDirectionId() + orientation
        directionNumber = if (directionNumber == 0) Directions.size() else directionNumber
        val newDirection: Direction = Directions.getDirection(directionNumber)
        return Position(position.x, position.y, newDirection)
    }

    override fun orientation(): Int = orientation

}