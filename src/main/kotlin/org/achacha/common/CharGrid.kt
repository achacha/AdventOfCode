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
class CharGrid {
    /**
     * Create grid of X,Y with default char as background filler
     * 0,0 is top left
     * @param sizeX X dimension (across)
     * @param sizeY Y dimension (down)
     * @param default filler character for the grid
     */
    constructor(sizeX: Int, sizeY: Int, default: Char = ' ') {
        this.grid = Array(sizeY) { CharArray(sizeX) { default } }
    }

    /**
     * Construct a grid using an existing Array of CharArray's
     */
    constructor(grid: Array<CharArray>) {
        this.grid = grid
    }

    /**
     * Copy constructor
     */
    constructor(that: CharGrid) {
        this.grid = Array(that.grid.size) { y -> CharArray(that.grid[y].size) { x -> that.grid[y][x] } }
    }

    /**
     * Internal representation of the grid
     */
    private val grid: Array<CharArray>

    private var gridBackup: Array<CharArray>? = null

    /**
     * Backup current grid
     */
    fun backup() {
        this.gridBackup = Array(grid.size) { y -> CharArray(grid[y].size) { x -> grid[y][x] } }
    }

    /**
     * Restore previously backed up grid
     */
    fun restore() {
        if (gridBackup == null) throw IllegalStateException("There is no backup to restore")
        for (y in 0 until grid.size) {
            for (x in 0 until grid[y].size) {
                gridBackup!![y][x] = grid[y][x]
            }
        }
    }

    /**
     * (X,Y) point
     */
    data class Point(
        var x: Int,
        var y: Int
    ) {
        /**
         * Delta point in x,y and return Point
         */
        fun delta(dx: Int = 0, dy: Int = 0): Point {
            return Point(x + dx, y + dy)
        }
    }

    /**
     * Read at position, return default if out of bounds
     *
     */
    fun getAt(x: Int, y: Int, default: Char): Char {
        return if (x < 0 || y < 0 || y >= grid.size || x >= grid[y].size) default
        else grid[y][x]
    }

    fun getAt(p: Point, default: Char): Char {
        return if (p.x < 0 || p.y < 0 || p.y >= grid.size || p.x >= grid[p.y].size) default
        else return grid[p.y][p.x]
    }

    fun setAt(x: Int, y: Int, c: Char) {
        grid[y][x] = c
    }

    fun setAt(pt: Point, c: Char) {
        grid[pt.y][pt.x] = c
    }

    fun maxX(y: Int): Int = grid[y].size
    fun maxY(): Int = grid.size

    override fun toString(): String {
        return grid.asString()
    }

    /**
     * Find first instance of c
     * Start top left (0,0), scan across X for every Y
     */
    fun findFirst(c: Char): Point? {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == c) return Point(x, y)
            }
        }
        return null
    }

    /**
     * Check if in grid
     */
    fun isInGrid(pt: Point): Boolean {
        return pt.y >= 0 && pt.y < grid.size && pt.x >= 0 && pt.x < grid[pt.y].size
    }

    /**
     * Check if NOT in grid
     */
    fun isNotInGrid(pt: Point): Boolean {
        return pt.y < 0 || pt.y >= grid.size || pt.x < 0 || pt.x >= grid[pt.y].size
    }

    fun findAll(c: Char): List<Point> {
        val result = mutableListOf<Point>()
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == c) result.add(Point(x, y))
            }
        }
        return result
    }
}