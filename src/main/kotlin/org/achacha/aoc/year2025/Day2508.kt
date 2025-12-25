package org.achacha.aoc.year2025

import org.achacha.common.Point3D
import org.achacha.common.load2StringLines
import java.util.*
import kotlin.math.roundToInt

class Day2508 {
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
            return "${points[one]} -> ${points[two]}"
        }
    }

    data class JunctionSet(
        val indexes: MutableSet<Int>
    )

    fun part1(resourcePath: String, count: Int): Long {
        // array of Point3D
        val points = load2StringLines(resourcePath).map(Point3D::toPoint3D).toTypedArray()
        // map of distance to edge
        val edges = TreeSet<Edge>()

        // Calculate distances
        var i = 0
        while (i < points.size - 1) {
            var j = i + 1
            while (j < points.size) {
                //println("Check: $i - $j")
                val distance = points[i].distanceTo(points[j])
                val edge = Edge(i, j, distance)
                edges.add(edge)
                ++j
            }
            ++i
        }

        // iterate and connect
        val pointSets = mutableSetOf<JunctionSet>()
        for (edge in edges.take(count)) {
            var psOne: JunctionSet? = null
            var psTwo: JunctionSet? = null
            for (ps in pointSets) {
                // Find if edge is in point set or spans 2 sets
                if (ps.indexes.contains((edge.one))) psOne = ps
                if (ps.indexes.contains((edge.two))) psTwo = ps
            }
            if (psOne == null && psTwo == null) {
                pointSets.add(JunctionSet(mutableSetOf(edge.one, edge.two)))
//                println("New (${edge.one}, ${edge.two}) ${edge.toString(points)}")
            } else if (psOne != null && psOne == psTwo) {
                // Same set
//                println("Adding(both same) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
                psOne.indexes.add(edge.one)
                psOne.indexes.add(edge.two)
            } else if (psOne != null && psTwo != null) {
                // Different set, join
                psOne.indexes.addAll(psTwo.indexes)
                psTwo.indexes.clear()
//                println("Merged (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
            } else if (psOne != null) {
//                println("Adding(one) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psOne")
                psOne.indexes.add(edge.one)
                psOne.indexes.add(edge.two)
            } else if (psTwo != null) {
//                println("Adding(two) (${edge.one}, ${edge.two}) ${edge.toString(points)} to $psTwo")
                psTwo.indexes.add(edge.one)
                psTwo.indexes.add(edge.two)
            } else {
                println("Missed: $edge")
            }
        }

        var product = 1L
        pointSets
            .sortedWith { setOne, setTwo -> setTwo.indexes.size - setOne.indexes.size }
            .take(3)
            .forEach { product *= it.indexes.size }

        return product
    }
}