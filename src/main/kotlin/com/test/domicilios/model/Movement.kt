package com.test.domicilios.model

import com.test.domicilios.exception.CustomException
import java.lang.IllegalArgumentException

sealed class Movement {

    abstract fun orientation(): Orientation
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
            override fun orientation() = Orientation(-1)
            override fun move(position: Position): Position = position.run {
                copy(direction = direction.nextDirection.invoke(orientation()))
            }
        }

        object RightWard : Rotation() {
            override fun orientation() = Orientation(1)
            override fun move(position: Position): Position = position.run {
                copy(direction = direction.nextDirection.invoke(orientation()))
            }
        }
    }

    object Forward : Movement() {
        override fun orientation() = Orientation(0)
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
        if (value > 1 || value < -1) {
            throw IllegalArgumentException("orientation value is not a number between 1 and -1. Value: $value")
        }
    }
}