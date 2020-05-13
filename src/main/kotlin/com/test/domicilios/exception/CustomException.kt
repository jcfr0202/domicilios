package com.test.domicilios.exception

class CustomException internal constructor(msg: String?) : RuntimeException(msg) {
    companion object {
        fun nonExistenConsumer(): CustomException {
            return CustomException("Cannot register a null consumer.")
        }

        fun invalidMovementInitial(initial: Char): CustomException {
            return CustomException("Movement with initial='$initial' has not been recognized")
        }

        fun invalidCardinalPoint(number: Int, length: Int): CustomException {
            return CustomException("'$number' does not belong to any direction. Directions are in range from 1 to $length.")
        }

        fun invalidNumberOfArguments(): CustomException {
            return CustomException("This program receives only one argument: inputFilesFolderPath (e.g. /usr/var/Desktop/)")
        }

        fun invalidForwardMovements(maxForwardMovements: Int): CustomException {
            return CustomException("Drone is not capable of going further than $maxForwardMovements blocks around.")
        }

        fun invalidNumberOfDeliveries(capacityOfDeliveries: Int): CustomException {
            return CustomException("Drone capacity specified (capacity=$capacityOfDeliveries) is not the same as the " +
                "number of routes specified in the input file")
        }
    }
}