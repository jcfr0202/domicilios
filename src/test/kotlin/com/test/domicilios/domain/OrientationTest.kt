package com.test.domicilios.domain

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import java.util.stream.Stream

internal class OrientationTest {

    @TestFactory
    fun `valid rotation orientations`(): Stream<DynamicTest> =
        Stream.of(-1, 1)
            .map { input ->
                DynamicTest.dynamicTest("Accepted: $input") { assertDoesNotThrow { Orientation(input) } }
            }
}