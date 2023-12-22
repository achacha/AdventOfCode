package org.achacha.aoc.year2023

import kotlin.math.pow

/**
 * https://adventofcode.com/2023/day/4
 */
class Day04 {
    data class Card(
        val id: String,
        val winningNumbers: Set<Int>,
        val myNumbers: Set<Int>,
        var copies: Long = 1L
    )

    val lineParser = Regex("Card ([^:]*): (.+) [|] (.+)")

    fun computePart1(input: String): Long {
        return parseInput(input).map { card ->
            matchNumbers(card)
        }.sumOf {
            if (it > 0) 2.0.pow((it-1).toDouble()).toLong()
            else 0
        }
    }

    fun computePart2(input: String): Long {
        val cards = parseInput(input)
        for(i in cards.indices) {
            matchNumbersAndPropagateCopies(cards, i)
        }
        return cards.sumOf(Card::copies)
    }

    fun parseInput(input: String): List<Card> {
        return input.lines().map {
            val result = lineParser.find(it)

            val id = result?.groupValues?.get(1) ?: throw IllegalArgumentException("Unable to find card ID in $it")
            val winningNumbers = result.groupValues[2].split(" ").map(String::trimIndent).filter(String::isNotBlank).map(String::toInt).toSet()
            val myNumbers = result.groupValues[3].split(" ").map(String::trimIndent).filter(String::isNotBlank).map(String::toInt).toSet()

            Card(id, winningNumbers, myNumbers)
        }.toList()
    }

    fun matchNumbers(card: Card): Int =
        card.myNumbers.count {
            card.winningNumbers.contains(it)
        }

    fun matchNumbersAndPropagateCopies(cards: List<Card>, index: Int): Int {
        val matches = cards[index].myNumbers.count {
            cards[index].winningNumbers.contains(it)
        }

        // Update copies
        var pos = index + 1
        var count = matches
        while (pos < cards.size && count > 0) {
            cards[pos].copies += cards[index].copies
            ++pos
            --count
        }

        //println("Card ${cards[index].id} matches=$matches  copies=${cards[index].copies}")
        return matches
    }
}
