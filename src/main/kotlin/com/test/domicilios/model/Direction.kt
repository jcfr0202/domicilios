package com.test.domicilios.model

sealed class Direction {

    abstract fun movementOverAxis(): Int

    val nextDirection: ((Orientation) -> Direction) = { orientation ->
        when (this) {
            is Vertical.North -> if (orientation.value == 1) { Horizontal.East } else { Horizontal.West }
            is Vertical.South -> if (orientation.value == 1) { Horizontal.West } else { Horizontal.East }
            is Horizontal.East -> if (orientation.value == 1) { Vertical.South } else { Vertical.North }
            is Horizontal.West -> if (orientation.value == 1) { Vertical.North } else { Vertical.South }
        }
    }

    sealed class Horizontal : Direction() {
        object East : Horizontal() {
            override fun movementOverAxis() = 1
            override fun toString(): String = this.javaClass.simpleName
        }

        object West : Horizontal() {
            override fun movementOverAxis() = -1
            override fun toString(): String = this.javaClass.simpleName
        }
    }

    sealed class Vertical : Direction() {
        object North : Vertical() {
            override fun movementOverAxis() = 1
            override fun toString(): String = this.javaClass.simpleName
        }

        object South : Vertical() {
            override fun movementOverAxis() = -1
            override fun toString(): String = this.javaClass.simpleName
        }
    }
}
