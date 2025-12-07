package org.achacha.common

/**
 * Char grid  (rectangular not square)
 *
 *  X 0123
 * Y
 * 0  line
 * 1  food
 *
 * Grid (Y, X)
 */
class CharGrid(private val grid: Array<CharArray>) {
    /**
     * Read at position, return default if out of bounds
     *
     */
    fun getAt(x: Int, y: Int, default: Char): Char {
        return if (x < 0 || y < 0 || y >= grid.size || x >= grid[y].size) default
        else grid[y][x]
    }

    fun maxX(y: Int): Int = grid[y].size
    fun maxY(): Int = grid.size
}