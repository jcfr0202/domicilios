package com.test.domicilios.model.movement

import com.test.domicilios.exception.CustomException

enum class Movements(private val initial: Char, private val movement: Movement) {
    FORWARD('A', Forward()),
    RIGHT('D', Right()),
    LEFT('I', Left());

    companion object {
        fun getMovement(initial: Char): Movement {
            return when (initial) {
                'A' -> FORWARD.movement
                'D' -> RIGHT.movement
                'I' -> LEFT.movement
                else -> throw CustomException.invalidMovementInitial(initial, movesInitials)
            }
        }

        private val movesInitials: String
            get() = values()
                .map { move: Movements -> "'" + move.initial + "'" }
                .toString()
    }

}