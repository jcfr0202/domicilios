package com.test.domicilios.model

import com.test.domicilios.model.direction.Direction
import com.test.domicilios.model.direction.East
import com.test.domicilios.model.direction.North
import com.test.domicilios.model.direction.South
import com.test.domicilios.model.direction.West
import com.test.domicilios.exception.CustomException

enum class Directions(number: Int, val direction: Direction) {
    NORTH(1, North()),
    EAST(2, East()),
    SOUTH(3, South()),
    WEST(4, West());

    companion object {
        fun getDirection(number: Int): Direction {
            return when (number) {
                1 -> NORTH.direction
                2 -> EAST.direction
                3 -> SOUTH.direction
                4 -> WEST.direction
                else -> throw CustomException.invalidCardinalPoint(number, values().size)
            }
        }

        fun size() = values().size
    }

}