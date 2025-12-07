package org.achacha.aoc.year2025

import org.achacha.common.load2StringLines
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.compareTo
import kotlin.math.max

class Day2503 {
    fun parseInput(resourcePath: String): List<String> {
        return load2StringLines(resourcePath)
    }

    fun part1(resourcePath: String): Long {
        val banks = parseInput(resourcePath)
        var result = 0L

        banks.forEach {
//            println(":=> $it")

            // find largest integer in all but last position
            var largest = 0
            var largestPos = -1
            for (i in 0..<it.length - 1) {
                if (it[i].digitToInt() > largest) {
                    largest = it[i].digitToInt()
                    largestPos = i
                }
            }
//            println("  largest=$largest at pos=$largestPos")

            // Find second digit to right of largest
            var secondLargest = 0
            for (i in largestPos + 1 until it.length) {
                secondLargest = max(secondLargest, it[i].digitToInt())
            }
//            println("  largest2=$secondLargest")

//            print("largest=${largest * 10 + secondLargest}")
            result += largest * 10 + secondLargest

//            println("\n----")
        }

        return result
    }

    fun remove1(row: String, i0: Int): String {
        return row.substring(0, i0) + row.substring(i0 + 1)
    }

    fun remove3(row: String, i0: Int, i1: Int, i2: Int): BigInteger {
        val mid = row.toCharArray()
            .mapIndexed { i, c -> if (i == i0 || i == i1 || i == i2) '_' else c }
            .filter { it != '_' }
            .joinToString("")

        return mid.toBigInteger()
    }

    fun findHighest(row: String): BigInteger {
        if (row.length == 12) return BigInteger(row)
        else {
            var currentHighest = BigInteger.ZERO
            for (x in 0 ..< row.length) {
                val subrow = remove1(row, x)
                val n = findHighest(subrow)
//                println(" Subtest:\t\t$subrow")
                if (n > currentHighest) currentHighest = n
            }
            return currentHighest
        }
    }

    fun removeFirst(row: String, c: Char): String {
        val pos = row.indexOf(c)
        if (pos == -1) throw IllegalArgumentException("Cannot find $c in $row")
        return row.substring(0, pos) + row.substring(pos + 1)
    }

    fun part2(resourcePath: String): BigInteger {
        val banks = parseInput(resourcePath)
        var result = BigInteger.ZERO

        banks.forEach { rowFull ->
            // Brute force
            println("Testing: $rowFull")
            var row = rowFull
            var lowest = '0'
            for(i in '0'..'9') {
                val newRow = row.filter { it != i }
                if (newRow.length < 12) {
                    lowest = i
                    break
                }
                row = newRow
            }
            println("Reduced1: $row")

            while (row.length > 12) {
                row = removeFirst(row, lowest)
            }
            println("Reduced2: $row")

            result = result.add(findHighest(row))
        }

        return result
    }
}