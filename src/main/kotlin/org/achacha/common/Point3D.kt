package org.achacha.common

import kotlin.math.sqrt

data class Point3D(
    private val x: Int = 0,
    private val y: Int = 0,
    private val z: Int = 0
) {
    companion object {
        fun toPoint3D(line: String): Point3D {
            line.split(",").let { parts ->
                return Point3D(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
            }
        }
    }

    /**
     * Distance between 2 points
     */
    fun distanceTo(other: Point3D): Double {
        val dx = (x - other.x).toDouble()
        val dy = (y - other.y).toDouble()
        val dz = (z - other.z).toDouble()
        return sqrt(dx * dx + dy * dy + dz * dz)
    }
}
