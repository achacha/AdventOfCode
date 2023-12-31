package org.achacha.aoc.year2023

/**
 * https://adventofcode.com/2023/day/9
 */
class Day09 {

    fun part1(input: String): Long {
        return parseInput(input)
            .mapIndexed { lino, linenums ->
                val sequenceArray = processLinePart1(linenums)

                // Sum up last of each line from end
                var result = 0L
                for (i in sequenceArray.size-1 downTo 0 step 1) {
                    result += sequenceArray[i].last()
                }
//                println("line[$lino]=$result")
                result
            }
            .sum()
    }

    fun part2(input: String): Long {
        return parseInput(input)
            .mapIndexed { lino, linenums ->
                val sequenceArray = processLinePart1(linenums)

                // Sum up last of each line from end
                var result = 0L
                for (i in sequenceArray.size-1 downTo 0 step 1) {
                    result = sequenceArray[i][0] - result
                }
//                println("line[$lino]=$result")
                result
            }
            .sum()
    }

    fun parseInput(input: String): List<List<Int>> {
        return input.lines().map { line ->
            line.split(" ").filter(String::isNotBlank).map(String::toInt)
        }
    }

    fun processLinePart1(nums: List<Int>): List<List<Int>> {
//        println("PROCESS: $nums")
        val sequenceArray = mutableListOf<List<Int>>()
        var currentRow = nums
        while (true) {
            sequenceArray.add(currentRow)
            val nextRow = getNextDiffRow(currentRow)

//            println("NEXT: $nextRow")

            if (nextRow.all { it == 0 }) break
            else currentRow = nextRow

        }
        return sequenceArray
    }

    fun getNextDiffRow(input: List<Int>): List<Int> {
        val output = mutableListOf<Int>()
        for (i in 0 ..< input.size-1) {
            output.add(input[i+1] - input[i])
        }
        return output
    }
}