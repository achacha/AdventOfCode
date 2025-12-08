package org.achacha.aoc.year2025

import org.achacha.common.load2StringLines

class Day2505 {
    class InputData {
        val fresh : MutableList<Pair<Long,Long>> = mutableListOf()
        val produce: MutableSet<Long> = mutableSetOf()
    }

    fun parseInputData(resourcePath: String): InputData {
        val result = InputData()
        val lines = load2StringLines(resourcePath)
        val iteratorLine = lines.iterator()

        // Read fresh ranges
        while (iteratorLine.hasNext()) {
            val line = iteratorLine.next()
            if (line.isEmpty()) break  // Done with range

            line.split("-").let {
                result.fresh.add(Pair(it[0].toLong(), it[1].toLong()))
            }
        }

        // Read produce IDs
        while (iteratorLine.hasNext()) {
            result.produce.add(iteratorLine.next().toLong())
        }
        return result
    }

    fun part1(resourcePath: String): Int {
        val inputData = parseInputData(resourcePath)

        var result = 0
        produceLoop@for (productId in inputData.produce) {
            for (f in inputData.fresh) {
                if (productId >= f.first && productId <= f.second) {
                    println("Product $productId found in ${f.first} ${f.second}")
                    result++
                    continue@produceLoop
                }
            }
        }
        return result
    }
}