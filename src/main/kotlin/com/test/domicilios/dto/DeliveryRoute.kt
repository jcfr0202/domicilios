package com.test.domicilios.dto

import com.test.domicilios.domain.Movement
import java.lang.IllegalArgumentException

class DeliveryRoute private constructor (private val value: String) {

    companion object  {
        private const val validRouteMovements = "ADI"

        fun createNew(value: String): DeliveryRoute {
            if (value.length < 5 || value.length > 200) {
                throw IllegalArgumentException("Route string must have between 5 and 200 characters. Route: '$value'")
            }
            if (!value.matches(Regex("^[AIDaid]*\$"))) {
                throw IllegalArgumentException("Route only accepts the following alphabetic characters: " +
                    "${validRouteMovements.toList()}. Route: '$value'")
            }
            return DeliveryRoute(value.toUpperCase())
        }
    }

    fun toEntity(): List<Movement> = value.toCharArray().map { Movement.getMovement(it) }
}