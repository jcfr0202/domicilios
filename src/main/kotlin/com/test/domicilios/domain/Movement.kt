package com.test.domicilios.domain

import com.test.domicilios.exception.CustomException
import java.lang.IllegalArgumentException

sealed class Movement {

    abstract fun move(position: Position): Position

    companion object {
        fun getMovement(initial: Char): Movement =
            when (initial) {
                'A' -> Forward
                'D' -> Rotation.RightWard
                'I' -> Rotation.LeftWard
                else -> throw CustomException.invalidMovementInitial(initial)
            }
    }

    sealed class Rotation : Movement() {
        object LeftWard : Rotation() {
            override fun move(position: Position): Position = position.run {
                copy(direction = direction.nextDirection.invoke(Orientation(-1)))
            }
        }

        object RightWard : Rotation() {
            override fun move(position: Position): Position = position.run {
                copy(direction = direction.nextDirection.invoke(Orientation(1)))
            }
        }
    }

    object Forward : Movement() {
        override fun move(position: Position) = position.run {
            when(direction) {
                is Direction.Horizontal -> copy(coordinateX = coordinateX + direction.movementOverAxis())
                is Direction.Vertical -> copy(coordinateY = coordinateY + direction.movementOverAxis())
            }
        }
    }
}

// Value Object
data class Orientation(val value: Int) {
    init {
        if (value != 1 && value != -1) {
            throw IllegalArgumentException("Orientation value must be either 1 or -1. Value: $value")
        }
    }
}