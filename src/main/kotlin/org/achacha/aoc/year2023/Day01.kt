package org.achacha.aoc.year2023

/**
 * https://adventofcode.com/2023/day/1
 */
class Day01 {
    /**
     * Extract digits from each line to form a number, sum all numbers
     */
    fun compute(input: String) =
        input.reader().readLines()
            .map(this::replaceWordWithDigit)
            .map { line ->
                println("CONV: $line")
                line.lowercase()
                    .filter { c ->
                        c.isDigit()
                    }
            }
            .filter {
                it.isNotEmpty()
            }.sumOf {
                val num = if (it.length >= 2) "${it.first()}${it.last()}"
                else if (it.length == 1) "${it.first()}${it.first()}"
                else "0"

                println("OUT : $num")
                num.toLong()
            }

    /**
     * Replace all word names for digit to actual digit
     * one -> 1, two ->2, etc
     */
    fun replaceWordWithDigit(input: String): String {
        val ca = input.toCharArray()

        println("IN: $input")
        ca.forEachIndexed { index, c ->
            if (String(ca).startsWith("one", startIndex = index)) {
                ca[index] = '1'
            } else if (String(ca).startsWith("two", startIndex = index)) {
                ca[index] = '2'
            } else if (String(ca).startsWith("three", startIndex = index)) {
                ca[index] = '3'
            } else if (String(ca).startsWith("four", startIndex = index)) {
                ca[index] = '4'
            } else if (String(ca).startsWith("five", startIndex = index)) {
                ca[index] = '5'
            } else if (String(ca).startsWith("six", startIndex = index)) {
                ca[index] = '6'
            } else if (String(ca).startsWith("seven", startIndex = index)) {
                ca[index] = '7'
            } else if (String(ca).startsWith("eight", startIndex = index)) {
                ca[index] = '8'
            } else if (String(ca).startsWith("nine", startIndex = index)) {
                ca[index] = '9'
            }
        }

        return String(ca)
    }
}