package org.achacha.common

/**
 * (X,Y) point
 */

data class GridPoint(
    var x: Int,
    var y: Int
) {
    /**
     * Delta point in x,y and return Point
     */
    fun delta(dx: Int = 0, dy: Int = 0): GridPoint {
        return GridPoint(x + dx, y + dy)
    }
}
