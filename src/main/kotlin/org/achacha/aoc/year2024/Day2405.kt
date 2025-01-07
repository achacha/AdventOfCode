package org.achacha.aoc.year2024

class Day2405(resourcePath: String) {

    private val debug = false

    val precedencePairs: MutableList<Pair<Int, Int>> = mutableListOf()
    val updateLists: MutableList<List<Int>> = mutableListOf()

    init {
        object {}.javaClass.getResourceAsStream(resourcePath)?.bufferedReader(Charsets.UTF_8).use {
            val lines = it?.readLines() ?: emptyList()
            var i = 0
            var line = lines[i++]
            // Precedence pairs
            while (line.isNotEmpty()) {
                // split |
                val numbers = line.trim().split("|").map(String::toInt)
                precedencePairs.add(Pair(numbers[0], numbers[1]))

                line = lines[i++]
            }

            do {
                line = lines[i++]

                // Parse update list
                updateLists.add(line.trim().split(",").map(String::toInt))
            } while (i < lines.size)
        }

    }

    fun part1(): Long {
        var sum = 0L
        updateLists.map { numbers ->
            var result = true
            // Check that precedence is correct
            for (i in numbers.indices.reversed()) {
                val updateNum = numbers[i]

                if (debug) println("Numbers: $numbers, updateNum: $updateNum")
                if (i == 0) break

                // Check that all occurrences in precedence do not contain RHS before this one
                result = result && precedencePairs
                    .filter { it.first == updateNum }
                    .all { pair->
                        var checkresult = true
                        if (debug) println("Checking: ${pair}")
                        for (j in i-1 downTo 0) {
                            if (debug) println("Precedence check: ${numbers[j]} and ${pair}")
                            if (numbers[j] == pair.second) {
                                if (debug) println("Precedence fail: ${numbers[j]} and ${pair}")
                                checkresult = false
                            }
                        }
                        checkresult
                    }

            }
            if (result) {
                sum += numbers[numbers.size/2]
            }
        }

        return sum
    }
}