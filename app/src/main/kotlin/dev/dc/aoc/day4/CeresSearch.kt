package dev.dc.aoc.day4

import java.io.FileNotFoundException

fun main() {
    val inputFileName = "day4.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val inputLines = inputFile.bufferedReader().readLines()

    part1(inputLines)
}

private fun part1(lines: List<String>) {
    val padding = "...."
    val cols = lines.first().length + padding.length
    val text = lines.joinToString(separator = padding)

    val indices = listOf(
        text.indices.map { it until it + 4 }, // horizontal
        text.indices.map { it until it + 4 * cols step cols }, // vertical
        text.indices.map { it until it + 4 * (cols + 1) step cols + 1 }, // forwards diagonal
        text.indices.map { it until it + 4 * (cols - 1) step cols - 1 }, // backwards diagonal
    ).flatten()

    val regex = "XMAS|SAMX".toRegex()
    val count = indices
        .filterNot { it.last >= text.length }
        .map { text.slice(it) }
        .count { regex.containsMatchIn(it) }

    println("Part 1: $count")
}
