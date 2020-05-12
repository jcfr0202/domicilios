package com.test.domicilios.model.movement

import com.test.domicilios.model.Position
import com.test.domicilios.model.direction.Direction
import com.test.domicilios.model.direction.DirectionX

class Forward : Movement {

    companion object {
        const val orientation = 0
    }

    override fun move(direction: Direction, position: Position): Position {
        val newPosition: Int
        return if (direction is DirectionX) {
            newPosition = position.x + direction.getMovementOverDirection()
            Position(newPosition, position.y, position.direction)
        } else {
            newPosition = position.y + direction.getMovementOverDirection()
            Position(position.x, newPosition, position.direction)
        }
    }

    override fun orientation(): Int = orientation

}
