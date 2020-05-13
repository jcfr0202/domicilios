package com.test.domicilios.application

import com.test.domicilios.domain.Direction
import com.test.domicilios.domain.Position
import java.io.File
import java.io.FileWriter

interface DeliveryService {
    fun deliver(routesFilePath: String, droneCapacity: Int)
}

class DeliveryServiceImpl(
    private val routerService: RouterService
) : DeliveryService {

    companion object {
        private const val REPORT_HEADER = "== Reporte de entregas =="
        private const val NEW_LINE = "\n"
    }

    private val outputDirectoryPath = this.javaClass.getResource("/output-files").path

    override fun deliver(routesFilePath: String, droneCapacity: Int) {
        generateReport(
            deliveryInfo = routerService.launchDrone(routesFilePath, droneCapacity, Position(0, 0, Direction.Vertical.North)),
            droneName = File(routesFilePath).nameWithoutExtension
        )
    }

    private fun generateReport(deliveryInfo: List<Position>, droneName: String) {
        val outputFileName = "$outputDirectoryPath/out${droneName.substring(2)}.txt"
        deliveryInfo.forEach {
            FileWriter(outputFileName).use { writer ->
                writer.write(REPORT_HEADER + NEW_LINE)
                deliveryInfo.forEach { writer.write(it.toString() + NEW_LINE) }
            }
        }
    }
}