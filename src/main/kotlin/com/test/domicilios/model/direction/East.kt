package com.test.domicilios.model.direction;

class East : DirectionX {

    companion object {
        private const val DIRECTION_ID = 2
        private const val MOVEMENT_OVER_COORDINATE = 1
        private const val DIRECTION_NAME = "East"
    }

    override fun getDirectionId(): Int {
        return DIRECTION_ID
    }

    override fun getMovementOverDirection(): Int {
        return MOVEMENT_OVER_COORDINATE
    }

    override fun getName(): String {
        return DIRECTION_NAME
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun toString(): String {
        return "Left"
    }

}
