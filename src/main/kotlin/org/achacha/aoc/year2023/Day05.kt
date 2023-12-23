package org.achacha.aoc.year2023

import kotlin.math.min

/**
 * https://adventofcode.com/2023/day/5
 */
class Day05(input: String) {
    /*
source-to-target map:
t0 s0 r0
t1 s1 r1
     */
    data class Segment(
        val sourceStart: Long,
        val targetStart: Long,
        val range: Long
    )

    data class Mapping(
        val sourceName: String,
        val targetName: String,
        val segments: List<Segment>
    )

    val mapRegex = Regex("(.+)-to-(.+) map:")

    lateinit var seeds: List<Long>
    val seedRanges = mutableListOf<Pair<Long, Long>>()

    // Source Name -> Mapping
    lateinit var mappings: Map<String, Mapping>

    init {
        parseInput(input)
    }

    /**
     * Given mapping name, calculate the correct target index from source index
     * @return Pair of target name and target index
     */
    fun getTarget(sourceName: String, sourceIndex: Long): Pair<String, Long> {
        val mapping = mappings[sourceName] ?: throw IllegalArgumentException("Unable to find mapping `$sourceName`")

        // iterate over segments to see if it was re-mapped
        for (segment in mapping.segments) {
            if (sourceIndex >= segment.sourceStart && sourceIndex < segment.sourceStart + segment.range) {
                // Found mapping segment
                return Pair(mapping.targetName, segment.targetStart + (sourceIndex - segment.sourceStart))
            }
        }

        // Not remapped
        return Pair(mapping.targetName, sourceIndex)
    }

    /**
     * List of the path to get from seed to location
     */
    fun findLocationFromSeed(index: Long): List<Pair<String, Long>> {
        val path = mutableListOf(Pair("seed", index))
        var targetName: String? = "seed"
        var targetIndex: Long = index
        while (targetName != "location") {
            targetName?.let {
                getTarget(it, targetIndex)
            }?.let {
                path.add(it)
                targetName = it.first
                targetIndex = it.second
            }
        }
        return path
    }

    /**
     * Part 1
     * @return lowest location or null if not found
     */
    fun getLowestLocationForSeeds(): Long {
        return seeds.minOfOrNull {
            findLocationFromSeed(it).last().second
        } ?: throw IllegalStateException("Unable to find any location for any of the seeds")
    }

    fun getLowestLocationForSeedRanges(): Long {
        var lowestSeedIndex = Long.MAX_VALUE
        seedRanges.forEach {
            println("Checking range: ${it.first}..<${it.first + it.second}")
            for (i in it.first..<(it.first + it.second)) {
                lowestSeedIndex = min(lowestSeedIndex, findLocationFromSeed(i).last().second)
            }
            println("lowest found=$lowestSeedIndex")
        }

        if (lowestSeedIndex == Long.MAX_VALUE) throw IllegalArgumentException("Did not find location for seed ranges")
        return lowestSeedIndex
    }

    fun getLowestLocationForSeedRangesRunOutOfMemory(): Long {
        return seedRanges.flatMap(this::expandRange).minOfOrNull {
            findLocationFromSeed(it).last().second
        } ?: throw IllegalStateException("Unable to find any location for any of the seeds")
    }

    fun expandRange(pair: Pair<Long, Long>): List<Long> {
        val expanded = mutableListOf<Long>()
        for (i in pair.first..<pair.first + pair.second) expanded.add(i)
        return expanded
    }

    fun parseInput(input: String) {
        // Special case for testing
        if (input.isEmpty()) return

        val lines = input.lines()
        var index = 1

        // read seeds line
        seeds = lines[0].substring(6).split(" ").map(String::trimIndent).filter(String::isNotBlank).map(String::toLong)
        if (seeds.size % 2 != 0) throw IllegalArgumentException("Seed line count must be even for range part2 to work")
        for (i in seeds.indices step 2) {
            seedRanges.add(Pair(seeds[i], seeds[i + 1]))
        }
        if (lines[index++].isNotBlank()) throw IllegalArgumentException("Expected blank line after `seeds:` line")

        // process maps
        val parsedMapping = mutableMapOf<String, Mapping>()
        while (index < lines.size) {
            // first line description
            val mapResult: MatchResult = mapRegex.find(lines[index])
                ?: throw IllegalArgumentException("Failed to parse map at line $index: `${lines[index]}`")
            val targetName = mapResult.groupValues[2]
            val sourceName = mapResult.groupValues[1]

            // parse mappings
            val segments = mutableListOf<Segment>()
            while (++index < lines.size) {
                if (lines[index].isBlank()) break
                val nums = lines[index].trimIndent().split(" ").map(String::toLong)
                segments.add(
                    Segment(
                        nums[1],
                        nums[0],
                        nums[2]
                    )
                )
            }
            ++index  // Skip over blank line after segments

            // Verify we don't have overlap
            var lastIndex = -1L
            val sortedSegments = segments.sortedBy { it.sourceStart }
            sortedSegments.forEach {
                if (it.sourceStart > lastIndex) lastIndex = it.sourceStart + it.range - 1
                else throw IllegalArgumentException("Found segment overlap: $it in $sortedSegments")
            }

            // Add segments to mapping and continue
            if (parsedMapping.containsKey(sourceName)) throw IllegalStateException("Did not expect mapping for `$sourceName` to already exist, check data near line: $index")
            parsedMapping[sourceName] = Mapping(
                sourceName,
                targetName,
                sortedSegments
            )
        }

        // verify that we can get from "seed" to "location"  (ignore circular bad data as it's not expected)
        var name: String? = "seed"
        while (name != null) {
            name = parsedMapping[name]?.targetName
            if (name == "location") break
        }
        if (name != "location") throw IllegalArgumentException("Mappings do not go from `seed` to `location`")

        this.mappings = parsedMapping
    }
}
