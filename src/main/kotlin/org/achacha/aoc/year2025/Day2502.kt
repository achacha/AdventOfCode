package org.achacha.aoc.year2025

import org.achacha.common.load2String
import org.achacha.common.stringNumberIncrementer
import java.math.BigInteger

class Day2502 {
    fun parseInputPart1(resourcePathToData: String): List<Pair<Long, Long>> {
        val rawData = load2String(resourcePathToData)

        val pairs = rawData
            .split(",")
            .map {
                val p = it.split("-")
                Pair(p[0].toLong(), p[1].toLong())
            }

        return pairs
    }

    fun parseInputPart2(resourcePathToData: String): List<Pair<String, String>> {
        val rawData = load2String(resourcePathToData)

        val pairs = rawData
            .split(",")
            .map {
                val p = it.split("-")
                Pair(p[0], p[1])
            }

        return pairs
    }

    /**
     * Checks if the whole string is 2 repeating string together
     */
    fun containsRepeating(num: String): String? {
        if (num.isEmpty()) return null
        if (num.length % 2 != 0) return null
        if (num[0] == '0') return null

        val lhs = num.take(num.length / 2)
        return if (num.substring(num.length / 2) == lhs) num else null
    }

    /**
     * Given a string check that is has a full repeating sequence
     */
    fun containsRepeatingSequential(num: String): String? {
        if (num.isEmpty()) return null
        if (num[0] == '0') return null

        // start with half size and work to 2
//        println("---Number: `$num`")
        for (i in num.length / 2 downTo 1) {
            val sb = StringBuilder(i * 3)
            val lhs = num.take(i)
            while(sb.length < num.length) {
                sb.append(lhs)
            }
//            println("Repeated: `$sb`")
            if (sb.toString() == num) return num
        }
        return null
    }

    fun part1(resourcePathToData: String): Long {
        val idRanges = parseInputPart1(resourcePathToData)
        var result = 0L

        // Iterate over ranges
        idRanges.forEach { (start, end) ->
            //println("--- Range: $start .. $end")
            var current = start.toString()
            while (current.toLong() <= end) {
                //print("Testing: $current")
                containsRepeating(current)?.let {
                    result += it.toLong()
                    //print(" ! found repeating id=$current  pattern=$it  result=$result")
                }
                //.also { println() }
                current = stringNumberIncrementer(current)
            }
            //println("---")
        }
        return result
    }

    fun part2(resourcePathToData: String): BigInteger {
        val idRanges = parseInputPart2(resourcePathToData)
        var result = BigInteger.ZERO

        // Iterate over ranges
        idRanges.forEach { (start, end) ->
//            println("--- Range: $start .. $end")
            var current = start
            while (current != end) {
//                println(": `$current`")
                containsRepeatingSequential(current)?.let {
                    result = result.add(BigInteger(it))
//                    println(" ! found repeating id=$current  pattern=$it  result=$result")
                }
                current = stringNumberIncrementer(current)
            }

            // Do last one
//            println(": `$current`")
            containsRepeatingSequential(current)?.let {
                result = result.add(BigInteger(it))
//                println(" ! found repeating id=$current  pattern=$it  result=$result")
            }
//            println("---")
        }
        return result
    }
}