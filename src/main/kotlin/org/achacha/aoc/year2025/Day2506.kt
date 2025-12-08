package org.achacha.aoc.year2025

import org.achacha.common.load2StringLines
import java.math.BigInteger

class Day2506 {
    class InputData1 {
        var rows: MutableList<IntArray> = mutableListOf()
        var opera: CharArray = charArrayOf()
    }

    fun parseInputData1(resourcePath: String): InputData1 {
        val lines = load2StringLines(resourcePath)
        val inputData1 = InputData1()

        // Parse numbers
        val regexSplit = Regex("\\s+")
        for (i in 0..<lines.size - 1) {
            val row0 = lines[i].trim().split(regexSplit)
            val row = row0.map(String::toInt).toIntArray()
            inputData1.rows.add(row)
        }
        // Parse operators
        inputData1.opera = lines[lines.size - 1].trim().split(regexSplit).map { it[0] }.toCharArray()

        return inputData1
    }

    fun part1(resourcePath: String): Long {
        var result = 0L
        val inputData = parseInputData1(resourcePath)
        for (i in inputData.opera.indices) {
            when (inputData.opera[i]) {
                '*' -> {
                    var columnOperation = 1L
                    for (j in inputData.rows.indices) {
                        columnOperation *= inputData.rows[j][i]
                    }
                    println("*[$i]=$columnOperation")
                    result += columnOperation
                }

                '+' -> {
                    var columnOperation = 0L
                    for (j in inputData.rows.indices) {
                        columnOperation += inputData.rows[j][i]
                    }
                    println("+[$i]=$columnOperation")
                    result += columnOperation
                }

                else -> throw IllegalArgumentException("Unknown operator ${inputData.opera[i]}")
            }
        }
        return result

    }

    fun part2(resourcePath: String): BigInteger {
        val lines = load2StringLines(resourcePath)
        var result: BigInteger = BigInteger.ZERO

        // normalize lengths
        val maxlength = lines[0].length
        for (line in lines) assert(line.length == maxlength)

        // Go right to left and when all lines have a space then go to next bank
        val maxheight = lines.size - 1
        var operator = ' '
        val nums = mutableListOf<BigInteger>()
        for (i in maxlength - 1 downTo 0) {
            if (lines[maxheight][i] != ' ') operator = lines[maxheight][i]
            val sb = StringBuilder()
            for (digit in 0..<maxheight) {
                sb.append(lines[digit][i])
            }
            val snum = sb.trim().toString()
            if (snum.isEmpty()) {
                result = processBank(operator, nums, result)

                // Clear and continue
                nums.clear()
                operator = ' '
            } else {
                // we have digits to convert to number
                nums.add(BigInteger(snum))
            }
        }

        // Bank 0
        result = processBank(operator, nums, result)

        return result
    }

    private fun processBank(operator: Char, nums: MutableList<BigInteger>, result: BigInteger): BigInteger {
        // vertical blank line
        var result1 = result
        assert(operator != ' ')

        when (operator) {
            '*' -> {
                var acc = BigInteger.ONE
                for (n in nums) {
                    acc = acc.multiply(n)
                }
                result1 = result1.add(acc)
//                println("*$nums:  acc=$acc  result=$result1")
            }

            '+' -> {
                var acc = BigInteger.ZERO
                for (n in nums) {
                    acc = acc.add(n)
                }
                result1 = result1.add(acc)
//                println("+$nums:  acc=$acc  result=$result1")
            }

            else -> throw IllegalArgumentException("Unknown operator `$operator`")
        }
        return result1
    }
}