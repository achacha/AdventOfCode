package org.achacha.aoc.year2023

class Day08(input: String) {

    val nodeRegex = Regex("([A-Z]+) = \\(([A-Z]+), ([A-Z]+)\\)")

    val startNodeName = "AAA"
    val endNodeName = "ZZZ"

    data class Node(
        val name: String,
        val leftName: String,
        var left: Node?,
        val rightName: String,
        var right: Node?,
    ) {
        override fun toString(): String {
            return "[$name = ($leftName${left.let { "*" }}, $rightName${right.let { "*" }})]"
        }
    }

    var nodes: MutableMap<String, Node> = mutableMapOf()

    lateinit var directions: String

    init {
        parseInput(input)
    }

    fun parseInput(input: String) {
        if (input.isBlank()) return

        val lines = input.lines()

        // Read directions
        directions = lines[0].trimIndent()
        if (lines[1].isNotBlank()) throw IllegalArgumentException("Expected second line to be blank")

        // Read nodes
        for (i in 2..<lines.size) {
            val match =
                nodeRegex.matchEntire(lines[i]) ?: throw IllegalArgumentException("Invalid node line: `${lines[i]}`")
            nodes.put(
                match.groupValues[1], Node(
                    name = match.groupValues[1],
                    leftName = match.groupValues[2],
                    left = null,
                    rightName = match.groupValues[3],
                    right = null
                )
            )
        }

        // Map nodes
        for (i in 2..<lines.size) {
            val match =
                nodeRegex.matchEntire(lines[i]) ?: throw IllegalArgumentException("Invalid node line: `${lines[i]}`")
            val node = nodes[match.groupValues[1]]
                ?: throw IllegalArgumentException("Node not found to map: `${match.groupValues[1]}`")
            node.left = nodes[match.groupValues[2]]
                ?: throw IllegalArgumentException("Node not found to map left: `${match.groupValues[2]}`")
            node.right = nodes[match.groupValues[3]]
                ?: throw IllegalArgumentException("Node not found to map left: `${match.groupValues[3]}`")
        }

        // Validation
        if (nodes[startNodeName] == null) throw IllegalArgumentException("Missing required node: $startNodeName")
        if (nodes[endNodeName] == null) throw IllegalArgumentException("Missing required node: $endNodeName")

//        print(nodes)
    }

    fun part1(): Int {
        var step = 0
        var node = nodes[startNodeName]
        while (true) {
            // position into direction
            val direction = directions[step % directions.length]

            node = when (direction) {
                'L' -> {
                    node?.left
                }

                'R' -> {
                    node?.right
                }

                else -> throw IllegalStateException("Invalid direction: `$direction`")
            } ?: throw IllegalStateException("Node path leads to unmapped node, not-expected, debugging required")
            ++step

            if (node.name == endNodeName)
                break
        }

        return step
    }
}