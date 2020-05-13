package com.test.domicilios.domain

import com.test.domicilios.dto.DeliveryRoute
import com.test.domicilios.exception.CustomException
import java.io.File
import java.io.FileNotFoundException
import java.lang.IllegalArgumentException

class Drone(
    routesFilePath: String,
    droneCapacity: Int
) {

    companion object {
        private const val MAX_FILE_SIZE_IN_BYTES_ALLOWED = 1024L
    }

    val deliveryRoutes: List<DeliveryRoute>

    init {
        val routesFile = File(routesFilePath)
        if (!routesFile.name.matches(Regex("(\\b[in]{2})([0-9])([0-9])([.txt]{4})"))) {
            throw IllegalArgumentException("File '$routesFilePath' does not have the correct name format: " +
                "in{number from 00 to 99}.txt, i.e. 'in01.txt'.")
        }
        routesFile
            .takeIf { it.exists() || !it.isDirectory }
            ?: throw FileNotFoundException("File '$routesFilePath' to retrieve the routes for drone was not found.")
        if (routesFile.length() == 0L) {
            throw IllegalArgumentException("File '$routesFilePath' is empty.")
        }
        if (routesFile.length() > MAX_FILE_SIZE_IN_BYTES_ALLOWED) {
            throw IllegalArgumentException("File '$routesFilePath' is more than 1024 bytes (1 KB) size, try with a smaller one.")
        }
        deliveryRoutes = routesFile.readLines(Charsets.UTF_8)
            .takeIf { it.count() == droneCapacity }
            ?.map { DeliveryRoute.createNew(it) }
            ?: throw CustomException.invalidNumberOfDeliveries(droneCapacity)
    }
}