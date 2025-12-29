package org.achacha.aoc.year2025

import org.achacha.common.Point3D
import org.achacha.common.load2StringLines
import java.util.*
import kotlin.math.roundToInt

class Day2508(
    val debug: Boolean = false
) {
    class Edge : Comparable<Edge> {
        /**
         * Index into array pf points
         */
        constructor(one: Int, two: Int, distance: Double) {
            this.one = one
            this.two = two
            this.distance = distance
        }

        val one: Int
        val two: Int
        val distance: Double

        override fun hashCode(): Int {
            var result = one
            result = 31 * result + two
            result = 31 * result + distance.hashCode()
            return result
        }

        override fun toString(): String {
            return "Edge(one=$one, two=$two, distance=$distance)"
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Edge) return false

            if (one != other.one) return false
            if (two != other.two) return false
            if (distance != other.distance) return false

            return true
        }

        override fun compareTo(other: Edge): Int {
            return (this.distance - other.distance).roundToInt()
        }

        fun toString(points: Array<Point3D>): String {
            return "${points[one]} -> ${points[two]} = $distance"
        }
    }

    data class JunctionSet(
        val indexes: MutableSet<Int>
    ) : Comparable<JunctionSet> {
        override fun compareTo(other: JunctionSet): Int {
            return if (indexes.size == other.indexes.size) {
                if (indexes.containsAll(other.indexes)) 1 else 0
            } else indexes.size - other.indexes.size
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is JunctionSet) return false

            if (indexes != other.indexes) return false

            return indexes.containsAll(other.indexes)
        }

        override fun hashCode(): Int {
            return indexes.hashCode()
        }
    }

    fun part1(resourcePath: String, count: Int): Long {
        // array of Point3D
        val points = load2StringLines(resourcePath).map(Point3D::toPoint3D).toTypedArray()
        // map of distance to edge
        val edges = mutableListOf<Edge>()

        // Calculate distances
        var i = 0
        while (i < points.size - 1) {
            var j = i + 1
            while (j < points.size) {
                val distance = points[i].distanceTo(points[j])
                val edge = Edge(i, j, distance)
                if (debug) println("P: ${edge.toString(points)}")
                edges.add(edge)
                ++j
            }
            ++i
        }

        edges.sort()
        if (debug) {
            println("\n---after parse edges---\n")
            for (e in edges) {
                println(e.toString(points))
            }
        }

        // iterate and connect
        println("\n---CHECKING---\n")
        val pointSets = mutableSetOf<JunctionSet>()
        for (edge in edges.take(count)) {
            if (debug) println("===Check: ${edge.toString(points)}")
            var psOne: JunctionSet? = null
            var psTwo: JunctionSet? = null
            for (ps in pointSets) {
                // Find if edge is in point set or spans 2 sets
                if (ps.indexes.contains((edge.one))) psOne = ps
                if (ps.indexes.contains((edge.two))) psTwo = ps
            }
            if (psOne == null && psTwo == null) {
                pointSets.add(JunctionSet(mutableSetOf(edge.one, edge.two)))
                if (debug) println("New (${edge.one}, ${edge.two}) ${edge.toString(points)}")
            } else if (psOne != null && psOne == psTwo) {
                // Same set
                if (debug) println("Adding(both same) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
                psOne.indexes.add(edge.one)
                psOne.indexes.add(edge.two)
            } else if (psOne != null && psTwo != null) {
                // Different set, join
                psOne.indexes.addAll(psTwo.indexes)
                psTwo.indexes.clear()
                if (debug) println("Merged (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
            } else if (psOne != null) {
                if (debug) println("Adding(one) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
                psOne.indexes.add(edge.one)
                psOne.indexes.add(edge.two)
            } else if (psTwo != null) {
                if (debug) println("Adding(two) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psTwo")
                psTwo.indexes.add(edge.one)
                psTwo.indexes.add(edge.two)
            } else {
                println("Missed: $edge")
            }
        }

        if (debug) {
            println("\n---final form---\n")
            for (s in pointSets) {
                println(s)
            }
        }

        var product = 1L
        pointSets
            .sortedWith { setOne, setTwo -> setTwo.indexes.size - setOne.indexes.size }
            .take(3)
            .forEach { product *= it.indexes.size }

        return product
    }

    fun part2(resourcePath: String): Long {
        // array of Point3D
        val points = load2StringLines(resourcePath).map(Point3D::toPoint3D).toTypedArray()
        // map of distance to edge
        val edges = mutableListOf<Edge>()
        val edgesByDistance = TreeMap<Double, Edge>()

        val fineDebug = false

        // Calculate distances
        var i = 0
        while (i < points.size - 1) {
            var j = i + 1
            while (j < points.size) {
                val distance = points[i].distanceTo(points[j])
                val edge = Edge(i, j, distance)
                if (debug && fineDebug) println("P: ${edge.toString(points)}")
                edges.add(edge)
                edgesByDistance[distance] = edge
                ++j
            }
            ++i
        }

        edges.sort()
        if (debug && fineDebug) {
            println("\n---after parse edges---\n")
            for (e in edges) {
                println(e.toString(points))
            }
        }

        if (debug && fineDebug) {
            println("\n---after parse edges by distance---\n")
            for (e in edgesByDistance.values) {
                println(e.toString(points))
            }
        }

        // iterate and connect
        println("\n---CONNECTING---\n")
        val pointSets = mutableSetOf<JunctionSet>()
        var done = false
        var lastEdge: Edge? = null
        while (!done) {
            for (edge in edgesByDistance.values) {
                if (debug) println("===Check: (${edge.one}, ${edge.two}):  ${edge.toString(points)}")
                var psOne: JunctionSet? = null
                var psTwo: JunctionSet? = null
                for (ps in pointSets) {
                    // Find if edge is in point set or spans 2 sets
                    if (ps.indexes.contains((edge.one))) psOne = ps
                    if (ps.indexes.contains((edge.two))) psTwo = ps
                }
                if (psOne == null && psTwo == null) {
                    pointSets.add(JunctionSet(mutableSetOf(edge.one, edge.two)))
                    if (debug) {
                        println("^^^New (${edge.one}, ${edge.two}) ${edge.toString(points)}")
                        println(pointSets.toString())
                    }
                } else if (psOne != null && psOne == psTwo) {
                    // Same set
                    if (debug && fineDebug) {
                        println("---Ignoring(both same) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
                        println(pointSets.toString())
                    }
                } else if (psOne != null && psTwo != null) {
                    // Different set, join
                    psOne.indexes.addAll(psTwo.indexes)
                    psTwo.indexes.clear()
                    if (debug) {
                        println("+++Merge before (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne; sets.size=${pointSets.size}")
                        println(pointSets.toString())
                    }
                    pointSets.removeIf { it.indexes.isEmpty() }
                    if (debug) {
                        println("+++Merged after (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne; sets.size=${pointSets.size}")
                        println(pointSets.toString())
                    }
                    lastEdge = edge
                } else if (psOne != null) {
                    psOne.indexes.add(edge.one)
                    psOne.indexes.add(edge.two)
                    if (debug) {
                        println("+1+Adding(one) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
                        println(pointSets.toString())
                    }
                    lastEdge = edge
                } else if (psTwo != null) {
                    psTwo.indexes.add(edge.one)
                    psTwo.indexes.add(edge.two)
                    if (debug) {
                        println("+2+Adding(two) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psTwo")
                        println(pointSets.toString())
                    }
                    lastEdge = edge
                } else {
                    println("???Missed: $edge")
                }
                if (debug) println()
            }
            pointSets.removeIf { it.indexes.isEmpty() }
            if (pointSets.size <= 1) {
                println("!!!Last connection: ${lastEdge?.toString(points)}")
                done = true
            }
        }

        if (debug) {
            println("\n---final form---\n")
            for (s in pointSets) {
                println(s)
            }
        }

        // 85319 * 99861
        return if (lastEdge != null) {
            (points[lastEdge.one].x).toLong() * (points[lastEdge.two].x).toLong()
        } else 0
    }
}