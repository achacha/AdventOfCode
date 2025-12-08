package org.achacha.aoc.year2025

import org.achacha.common.load2StringLines
import org.apache.commons.lang3.tuple.MutablePair
import kotlin.math.max
import kotlin.math.min

class Day2505 {
    class InputData {
        var fresh : MutableList<MutablePair<Long,Long>> = mutableListOf()
        var produce: MutableSet<Long> = mutableSetOf()
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
                result.fresh.add(MutablePair(it[0].toLong(), it[1].toLong()))
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
                if (productId >= f.left && productId <= f.right) {
                    println("Product $productId found in ${f.left} ${f.right}")
                    result++
                    continue@produceLoop
                }
            }
        }
        return result
    }

    /**
     * Return how mny ranges were compacted
     */
    fun compactRanges(inputData: InputData): Int {
        var result = 0

        var i = 0
        while (i < inputData.fresh.size) {
            assert(inputData.fresh[i].left <= inputData.fresh[i].right)
            for (j in i+1 until inputData.fresh.size) {
                // No intersection, do nothing
                if (
                    inputData.fresh[i].right < inputData.fresh[j].left
                    || inputData.fresh[j].right < inputData.fresh[i].left
                ) continue

                // overlap
                else if (
                    inputData.fresh[i].right >= inputData.fresh[j].left
                    || inputData.fresh[i].left <= inputData.fresh[j].right
                ) {
                    inputData.fresh[i].left = min(inputData.fresh[i].left, inputData.fresh[j].left)
                    inputData.fresh[i].right = max(inputData.fresh[j].right, inputData.fresh[i].right)
                    inputData.fresh[j].left = -1L
                    inputData.fresh[j].right = -1L
                }

                // contained
                else if (
                    (inputData.fresh[i].left <= inputData.fresh[j].left && inputData.fresh[i].right <= inputData.fresh[j].right)
                    || (inputData.fresh[j].left <= inputData.fresh[i].left && inputData.fresh[j].right <= inputData.fresh[i].right)
                ) {
                    inputData.fresh[i].left = min(inputData.fresh[i].left, inputData.fresh[j].left)
                    inputData.fresh[i].right = max(inputData.fresh[j].right, inputData.fresh[i].right)
                    inputData.fresh[j].left = -1L
                    inputData.fresh[j].right = -1L
                }
            }

            // compact and remove (-1,-1)
            val initSize = inputData.fresh.size
            //println("PreCompact: i=$i  ranges=${inputData.fresh}")
            inputData.fresh = inputData.fresh.filter { it.left != -1L }.toMutableList()
            if (inputData.fresh.size < initSize) {
                i = 0
                result++
            } else i++
            //println("PostCompact: i=$i  ranges=${inputData.fresh}")
        }

        return result
    }

    fun part2(resourcePath: String): Long {
        val inputData = parseInputData(resourcePath)
        var result = 0L

        while (compactRanges(inputData) > 0) {
            print(".")
        }

        // Add all values in compacted ranges
        for (range in inputData.fresh) {
            result += (range.right - range.left + 1)
        }

        return result
    }
}