package org.achacha.common

/**
 * Char grid  (rectangular not square)
 *
 *  X 0123
 * Y
 * 0  
 * 1  
 *
 * Grid (Y, X)
 */
class IntGrid {
    /**
     * Create grid of X,Y with default char as background filler
     * 0,0 is top left
     * Default fill 0
     * @param sizeX X dimension (across)
     * @param sizeY Y dimension (down)
     */
    constructor(sizeX: Int, sizeY: Int) {
        this.grid = Array(sizeY) { IntArray(sizeX) }
    }

    /**
     * Construct a grid using an existing Array of IntArray's
     */
    constructor(grid: Array<IntArray>) {
        this.grid = grid
    }

    /**
     * Copy constructor
     */
    constructor(that: IntGrid) {
        this.grid = Array(that.grid.size) { y -> IntArray(that.grid[y].size) { x -> that.grid[y][x] } }
    }

    /**
     * Internal representation of the grid
     */
    private val grid: Array<IntArray>

    private var gridBackup: Array<IntArray>? = null

    /**
     * Backup current grid
     */
    fun backup() {
        this.gridBackup = Array(grid.size) { y -> IntArray(grid[y].size) { x -> grid[y][x] } }
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
     * Read at position, return default if out of bounds
     *
     */
    fun getAt(x: Int, y: Int, default: Int): Int {
        return if (x < 0 || y < 0 || y >= grid.size || x >= grid[y].size) default
        else grid[y][x]
    }

    fun getAt(p: GridPoint, default: Int): Int {
        return if (p.x < 0 || p.y < 0 || p.y >= grid.size || p.x >= grid[p.y].size) default
        else return grid[p.y][p.x]
    }

    fun setAt(x: Int, y: Int, c: Int) {
        grid[y][x] = c
    }

    fun setAt(pt: GridPoint, c: Int) {
        grid[pt.y][pt.x] = c
    }

    fun inc(x: Int, y: Int) {
        grid[y][x]++
    }

    fun inc(pt: GridPoint) {
        grid[pt.y][pt.x]++
    }

    /**
     * Apply function at GridPoint
     */
    fun operate(pt: GridPoint, functor: (i: Int) -> Int) {
        grid[pt.y][pt.x] = functor(grid[pt.y][pt.x])
    }

    fun maxX(y: Int): Int = grid[y].size
    fun maxY(): Int = grid.size

    override fun toString(): String {
        return grid.joinToString("\n", transform = { it.joinToString("") })
    }

    /**
     * Find first instance of c
     * Start top left (0,0), scan across X for every Y
     */
    fun findFirst(c: Int): GridPoint? {
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == c) return GridPoint(x, y)
            }
        }
        return null
    }

    /**
     * Check if in grid
     */
    fun isInGrid(pt: GridPoint): Boolean {
        return pt.y >= 0 && pt.y < grid.size && pt.x >= 0 && pt.x < grid[pt.y].size
    }

    /**
     * Check if NOT in grid
     */
    fun isNotInGrid(pt: GridPoint): Boolean {
        return pt.y < 0 || pt.y >= grid.size || pt.x < 0 || pt.x >= grid[pt.y].size
    }

    fun findAll(c: Int): List<GridPoint> {
        val result = mutableListOf<GridPoint>()
        for (y in grid.indices) {
            for (x in grid[y].indices) {
                if (grid[y][x] == c) result.add(GridPoint(x, y))
            }
        }
        return result
    }
}