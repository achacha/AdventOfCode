package org.achacha.common

/**
 * Given resource path
 * Real lines separated by delimiter, clean white space
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
 * Real lines separated by delimiter, clean white space
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
 * Given Array of Int, return new Array of Int with element at index i removed
 */
fun removeOne(line: Array<Int>, i: Int): Array<Int> =
    line.mapIndexedNotNull { index, c ->
        if (index == i) null else c
    }.toTypedArray()
