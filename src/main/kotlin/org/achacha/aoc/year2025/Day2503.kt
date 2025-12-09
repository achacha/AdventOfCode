package org.achacha.aoc.year2025

import org.achacha.common.load2StringLines
import java.math.BigInteger
import java.util.*
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

    fun findLargestMonotonicStack(row: String): String {
        val stack = Stack<Char>()

        stack.push('0')
        for (i in 0 ..< row.length) {
            val c = row[i]
            if (row.length - i <= 12 - stack.size) { stack.push(c); continue; }

            var top = stack.peek()
            while (
                stack.isNotEmpty()
                && c > top
                && stack.size + (row.length - i) > 12
            ) {
                stack.pop()
                if (stack.isNotEmpty())
                    top = stack.peek()
            }
            if (stack.size < 12) stack.push(c)
        }

        return stack.joinToString("")
    }

    fun part2(resourcePath: String): BigInteger {
        val banks = parseInput(resourcePath)
        var result = BigInteger.ZERO

        banks.forEach { rowFull ->
            val num = findLargestMonotonicStack(rowFull)
            println("$rowFull -> $num")
            result = result.add(BigInteger(num))
        }

        return result
    }
}