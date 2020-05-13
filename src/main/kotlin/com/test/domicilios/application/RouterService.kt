package com.test.domicilios.application

import com.test.domicilios.domain.Drone
import com.test.domicilios.domain.Position
import com.test.domicilios.dto.DeliveryRoute

interface RouterService {
    fun launchDrone(routesFilePath: String, droneCapacity: Int, initialPosition: Position): List<Position>
}

object RouterServiceImpl : RouterService {

    override fun launchDrone(routesFilePath: String, droneCapacity: Int, initialPosition: Position): List<Position> {
        val deliveryInfo: MutableList<Position> = mutableListOf()
        Drone(routesFilePath, droneCapacity)
            .deliveryRoutes
            .fold(initialPosition) { route, pos ->
                followDeliveryRoute(pos, route).last()
                    .also { deliveryPosition -> deliveryInfo.add(deliveryPosition) }
            }
        return deliveryInfo
    }

    private fun followDeliveryRoute(deliveryRoute: DeliveryRoute, position: Position): List<Position> {
        val trace: MutableList<Position> = mutableListOf()
        deliveryRoute.toEntity().fold(position) { currentPosition, movement ->
            currentPosition
                .merge(nextPosition = movement.move(position = Position(0, 0, currentPosition.direction)))
                .also { trace.add(it) }
        }
        return trace
    }
}