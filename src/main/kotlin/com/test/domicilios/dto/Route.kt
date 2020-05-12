package com.test.domicilios.dto

import java.lang.IllegalArgumentException

class Route private constructor (val value: String) {

    companion object  {
        private const val validRouteMovements = "ADI"

        fun createNew(value: String): Route {
            if (value.length > 100) {
                throw IllegalArgumentException("Route cannot have more than 100 characters. Route: $value")
            }
            if (!value.matches(Regex("^[AIDaid]*\$"))) {
                throw IllegalArgumentException("Route only accepts the following alphabetic characters: " +
                    "${validRouteMovements.toList()}. Route: '$value'")
            }
            return Route(value.toUpperCase())
        }
    }
}