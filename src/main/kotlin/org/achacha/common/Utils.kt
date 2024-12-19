package org.achacha.common

/**
 * Given resource path
 * Real lines separated by delimiter, clean white space
 * @return List of Array of String
 */
fun load2column(resourcePath: String, delim: String): List<Array<String>> {
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
fun load2column_Int(resourcePath: String, delim: String): List<Array<Int>> {
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
