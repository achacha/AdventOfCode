package org.achacha.aoc.year2023

class Day07(input: String) {
    data class HandBid(
        val hand: String,
        val bid: Long,
    )

    val hands = mutableListOf<HandBid>()

    init {
        if (input.isNotBlank()) {
            input.lines().forEach { line ->
                hands.add(HandBid(line.substring(0, 5), line.substring(6).trimIndent().toLong()))
            }
        }
    }

    fun part1(): Long {
        return getOrderedHandsPart1().mapIndexed { index, handBid -> (index + 1) * handBid.bid }.sum()
    }

    fun part2(): Long {
        return getOrderedHandsPart2().mapIndexed { index, handBid -> (index + 1) * handBid.bid }.sum()
    }

    fun comparePart1(l: HandBid, r: HandBid): Int {
        val lv = handValuePart1(l.hand)
        val rv = handValuePart1(r.hand)
        return if (lv > rv) 1 else if (lv < rv) -1 else compareEqualValueHandsPart1(l.hand, r.hand)
    }

    fun comparePart2(l: HandBid, r: HandBid): Int {
        val lv = handValuePart2(l.hand)
        val rv = handValuePart2(r.hand)
        return if (lv > rv) 1 else if (lv < rv) -1 else compareEqualValueHandsPart2(l.hand, r.hand)
    }

    /**
     * For sorting purposes
     * high card - 1
     * one pair - 2
     * two pair - 3
     * 3 of a kind - 5
     * full house - 8
     * 4 of a kind - 13
     * 5 of a kind - 21
     */
    fun handValuePart1(hand: String): Int = checkHand(hand.toCharArray().sorted())

    /**
     * Brute force implementation
     */
    fun handValuePart2(hand: String): Int {
        // Rotate hand through all variations
        val cardValues = "23456789TQKA"  //12
        val sorted = hand.toCharArray().filter { it != 'J' }.sorted()
        if (sorted.size == 5) {
            // No jockers
            return checkHand(sorted)
        }
        if (sorted.isEmpty()) {
            // All jockers
            return 21
        }

        // Setup array for rotating
        val jockers = IntArray(5 - sorted.size)
        for (i in jockers.indices) jockers[i] = 0

        // Test initial hand and advance the jocker wheels
        var highestHandValue = 0
        var highestHand: List<Char>?
        while (true) {
            // Test hand
            val workHand = sorted.toMutableList()
            for (i in jockers.indices)
                workHand.add(cardValues[jockers[i]])

            // Keep highest
            val checkedValue = checkHand(workHand.toList())
            if (checkedValue > highestHandValue) {
                highestHand = workHand
                highestHandValue = checkedValue
                //println("Found new highest: ($highestHandValue): $highestHand")
            }

            // Advance jocker wheel
            var found = false
            var i = 0
            while (i < jockers.size && !found) {
                jockers[i] = jockers[i] + 1
                if (jockers[i] >= 12) {
                    // overflow
                    jockers[i] = 0
                    i++
                    continue
                }
                found = true
            }
            //println("Jockers: ${jockers.toList()}")

            // If done we have run out of all combos
            if (!found) break
        }

        return highestHandValue
    }

    fun checkHand(sorted: List<Char>): Int {
        val grouped = sorted.groupBy { it }
        if (grouped.any { it.value.size == 5 }) return 21
        if (grouped.any { it.value.size == 4 }) return 13
        if (grouped.any { it.value.size == 3 } && grouped.any { it.value.size == 2 }) return 8
        if (grouped.any { it.value.size == 3 }) return 5
        if (grouped.filter { it.value.size == 2 }.count() == 2) return 3
        if (grouped.any { it.value.size == 2 }) return 2

        return 1
    }

    /**
     * @return left > right 1, left < right -1, left == right 0
     */
    fun compareEqualValueHandsPart1(left: String, right: String): Int {
        val leftMapped = left.toCharArray().map { mapCardPart1(it) }
        val rightMapped = right.toCharArray().map { mapCardPart1(it) }

        for (i in leftMapped.indices) {
            if (leftMapped[i] > rightMapped[i]) return 1
            else if (leftMapped[i] < rightMapped[i]) return -1
        }
        return 0
    }

    fun compareEqualValueHandsPart2(left: String, right: String): Int {
        val leftMapped = left.toCharArray().map { mapCardPart2(it) }
        val rightMapped = right.toCharArray().map { mapCardPart2(it) }

        for (i in leftMapped.indices) {
            if (leftMapped[i] > rightMapped[i]) return 1
            else if (leftMapped[i] < rightMapped[i]) return -1
        }
        return 0
    }

    fun mapCardPart1(c: Char): Int =
        when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 11
            'T' -> 10
            '9' -> 9
            '8' -> 8
            '7' -> 7
            '6' -> 6
            '5' -> 5
            '4' -> 4
            '3' -> 3
            '2' -> 2
            else -> 0
        }

    fun mapCardPart2(c: Char): Int =
        when (c) {
            'A' -> 14
            'K' -> 13
            'Q' -> 12
            'J' -> 1
            'T' -> 10
            '9' -> 9
            '8' -> 8
            '7' -> 7
            '6' -> 6
            '5' -> 5
            '4' -> 4
            '3' -> 3
            '2' -> 2
            else -> 0
        }

    fun getOrderedHandsPart1(): List<HandBid> {
        return hands.sortedWith { l, r ->
            comparePart1(l, r)
        }
    }

    fun getOrderedHandsPart2(): List<HandBid> {
        return hands.sortedWith { l, r ->
            comparePart2(l, r)
        }
    }
}
