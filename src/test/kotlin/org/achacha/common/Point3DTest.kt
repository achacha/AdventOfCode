package org.achacha.common

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

class Point3DTest {
    @Test
    fun distanceTo() {
        assertEquals(sqrt(3.0), Point3D.toPoint3D("0,0,0").distanceTo(Point3D.toPoint3D("1,1,1")))
        assertEquals(sqrt(12.0), Point3D.toPoint3D("-1,-1,-1").distanceTo(Point3D.toPoint3D("1,1,1")))
        assertEquals(sqrt(50.0), Point3D().distanceTo(Point3D(3, 4, 5)))
    }
}