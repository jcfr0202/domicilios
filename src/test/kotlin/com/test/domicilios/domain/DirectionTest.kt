package com.test.domicilios.domain

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsInstanceOf
import org.hamcrest.CoreMatchers.`is` as Is
import org.junit.jupiter.api.Test

internal class DirectionTest {

    @Test
    fun north() {
        Direction.Vertical.North.apply {
            assertThat(movementOverAxis(), Is(equalTo(1)))
            assertThat(toString(), Is(equalTo("North")))
            assertThat(nextDirection.invoke(Orientation(1)), IsInstanceOf(Direction.Horizontal.East::class.java))
            assertThat(nextDirection.invoke(Orientation(-1)), IsInstanceOf(Direction.Horizontal.West::class.java))
        }
    }

    @Test
    fun south() {
        Direction.Vertical.South.apply {
            assertThat(movementOverAxis(), Is(equalTo(-1)))
            assertThat(toString(), Is(equalTo("South")))
            assertThat(nextDirection.invoke(Orientation(1)), IsInstanceOf(Direction.Horizontal.West::class.java))
            assertThat(nextDirection.invoke(Orientation(-1)), IsInstanceOf(Direction.Horizontal.East::class.java))
        }
    }

    @Test
    fun east() {
        Direction.Horizontal.East.apply {
            assertThat(movementOverAxis(), Is(equalTo(1)))
            assertThat(toString(), Is(equalTo("East")))
            assertThat(nextDirection.invoke(Orientation(1)), IsInstanceOf(Direction.Vertical.South::class.java))
            assertThat(nextDirection.invoke(Orientation(-1)), IsInstanceOf(Direction.Vertical.North::class.java))
        }
    }

    @Test
    fun west() {
        Direction.Horizontal.West.apply {
            assertThat(movementOverAxis(), Is(equalTo(-1)))
            assertThat(toString(), Is(equalTo("West")))
            assertThat(nextDirection.invoke(Orientation(1)), IsInstanceOf(Direction.Vertical.North::class.java))
            assertThat(nextDirection.invoke(Orientation(-1)), IsInstanceOf(Direction.Vertical.South::class.java))
        }
    }

}