package com.test.domicilios.model.movement

import com.test.domicilios.model.Position
import com.test.domicilios.model.direction.Direction

interface Movement {
    fun orientation(): Int

    fun move(direction: Direction, position: Position): Position
}
