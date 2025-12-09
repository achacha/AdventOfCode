package org.achacha.common

/**
 * Given resource path
 * Read lines separated by delimiter, clean white space
 * @return List of Array of String
 */
fun load2column(resourcePath: String, delim: String = " "): List<Array<String>> {
    return object {}.javaClass.getResourceAsStream(resourcePath)?.bufferedReader(Charsets.UTF_8).use {
        val lines = it?.readLines()
        lines?.map { line ->
            line.trimIndent().split(delim).map { it.trimIndent() }.filter(String::isNotBlank).toTypedArray()
        }?.toList() ?: emptyList()
    }
}

/**
 * Given resource path
 * Read lines separated by delimiter, clean white space
 * @return List of Array of Int
 */
fun load2column_Int(resourcePath: String, delim: String = " "): List<Array<Int>> {
    return object {}.javaClass.getResourceAsStream(resourcePath)?.bufferedReader(Charsets.UTF_8).use {
        val lines = it?.readLines()
        lines?.map { line ->
            line
                .trimIndent()
                .split(delim)
                .map { it.trimIndent() }
                .filter(String::isNotBlank)
                .map(Integer::valueOf)
                .toTypedArray()
        }?.toList() ?: emptyList()
    }
}

fun load2ArrayOfCharArray(resourcePath: String): Array<CharArray> {
    return object {}.javaClass.getResourceAsStream(resourcePath)?.bufferedReader(Charsets.UTF_8).use {
        val lines = it?.readLines() ?: emptyList()
        lines.map(String::toCharArray).toTypedArray()
    }
}

/**
 * Loads text grid into CharGrid
 */
fun load2CharGrid(resourcePath: String): CharGrid {
    return CharGrid(load2ArrayOfCharArray(resourcePath))
}

/**
 * Given resource path, read it into a String
 * @return Contents as String
 */
fun load2String(resourcePath: String): String =
    object {}.javaClass.getResourceAsStream(resourcePath)?.bufferedReader(Charsets.UTF_8).use { it?.readText() } ?: ""

/**
 * Given resource path read lines into List<String>
 * @return Contents as String lines in a list
 */
fun load2StringLines(resourcePath: String): List<String> =
    object {}.javaClass.getResourceAsStream(resourcePath)?.bufferedReader(Charsets.UTF_8).use { it?.readLines() } ?: emptyList()

/**
 * Show array as string
 */
fun Array<CharArray>.asString(): String = this.joinToString("\n", transform = { it.joinToString("") })

/**
 * Show array as string
 */
fun Array<Int>.asString(): String = this.joinToString(" ")

fun List<Array<Int>>.asString(): String = this.joinToString("\n", transform = { it.asString() })

/**
 * Given Array of Int, return new Array of Int with element at index `i` removed
 */
fun removeOne(line: Array<Int>, i: Int): Array<Int> =
    line.mapIndexedNotNull { index, c ->
        if (index == i) null else c
    }.toTypedArray()

/**
 * Given a square grid represented by a List<String>
 * @return character at offset `x` across and `y` down, \u0000 if out of bounds
 */
fun List<String>.getAt(x: Int, y: Int): Char =
    if (y >= this.size || y < 0) '\u0000'
    else if (x >= this[y].length || x < 0) '\u0000'
    else this[y][x]

/**
 * Add 1 to the strings ASCII value
 * Treats characters as their ASCII value so a->b  0->1 etc
 * @param num Input to add 1 to
 * @return num + 1
 */
fun stringNumberIncrementer(num: String): String {
    val r = StringBuilder(num.length + 1)
    var pos = num.length - 1
    var carry = 0
    var adder = 1
    while (pos >= 0) {
        val n = num[pos] + adder + carry
        r.append(if (n > '9') { carry = 1; n - 10 } else { carry = 0; n })
        pos--
        adder = 0
    }

    if (carry > 0) r.append(carry)

    return r.toString().reversed()
}
