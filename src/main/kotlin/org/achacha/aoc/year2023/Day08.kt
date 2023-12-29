package org.achacha.aoc.year2023

class Day08(input: String) {

    val nodeRegex = Regex("(\\w+) = \\((\\w+), (\\w+)\\)")

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

    fun part2(): Long {
        // Gather all nodes that end in A
        val nodeList: MutableList<Node> = nodes
            .filterKeys { it.endsWith('A') }
            .values
            .toMutableList()

        var step = 0L
        val dirlen: Long = directions.length.toLong()
        while (true) {
            val direction = directions[(step++ % dirlen).toInt()]
            print(nodeList.map { it.name }.joinToString(",") + "-[$direction]->")

            // Advance all nodes in list
            for (i in nodeList.indices) {
                nodeList.set(
                    i, when (direction) {
                        'L' -> {
                            nodeList[i].left
                        }

                        'R' -> {
                            nodeList[i].right
                        }

                        else -> throw IllegalStateException("Invalid direction: `$direction`")
                    }
                        ?: throw IllegalStateException("Node path leads to unmapped node, not-expected, debugging required")
                )
            }

            // Check if all nodes in list end with Z
            println(nodeList.map { it.name }.joinToString(","))
            if (nodeList.all { it.name.endsWith('Z') }) break
        }

        return step
    }

    fun part2period(): Long {
        // Gather all nodes that end in A
        val nodeList: MutableList<Node> = nodes
            .filterKeys { it.endsWith('A') }
            .values
            .toMutableList()

        var result = mutableListOf<Long>()
        val dirlen: Long = directions.length.toLong()
        for (i in nodeList.indices) {
            var step = 0L
            var ptr: Node? = nodeList[i]
            while (ptr?.name?.endsWith('Z') == false) {
                val direction = directions[(step++ % dirlen).toInt()]
                //print("${ptr.name}-[$direction]->")

                ptr = when (direction) {
                    'L' -> {
                        ptr.left
                    }

                    'R' -> {
                        ptr.right
                    }

                    else -> throw IllegalStateException("Invalid direction: `$direction`")

                }

                //println("${ptr?.name}")
                if (ptr?.name?.endsWith('Z') == true) {
                    //println(".....$step")
                    result.add(step)
                    continue
                }
            }
        }

        return lcm(result)
    }

    private fun gcd(a: Long, b: Long): Long {
        var a = a
        var b = b
        while (b > 0) {
            val temp = b
            b = a % b // % is remainder
            a = temp
        }
        return a
    }

    private fun gcd(input: List<Long>): Long {
        var result = input[0]
        for (i in 1..<input.size) result = gcd(result, input[i])
        return result
    }

    private fun lcm(a: Long, b: Long): Long {
        return a * (b / gcd(a, b))
    }

    private fun lcm(input: List<Long>): Long {
        var result = input[0]
        for (i in 1..<input.size) result = lcm(result, input[i])
        return result
    }
}