package org.achacha.aoc.year2024

import org.achacha.common.load2column_Int
import kotlin.math.abs

class Day2401 {
    fun part1(resourcePath: String): Int {
        // read data
        val lineData = load2column_Int(resourcePath, " ")

        // split into 2 lists
        val column1 = lineData.map { it[0] }.sorted()
        val column2 = lineData.map { it[1] }.sorted()

        return column1.mapIndexed { index, it ->
            abs(it - column2[index])
        }.sum()
    }

    fun part2(resourcePath: String): Int {
        // read data
        val lineData = load2column_Int(resourcePath, " ")

        // split into 2 lists
        val column1 = lineData.map { it[0] }
        val column2 = lineData.map { it[1] }

        return column1.sumOf {
            // count how many times it occurs in column 2
            column2.count { it2 -> it == it2 } * it
        }
    }
}