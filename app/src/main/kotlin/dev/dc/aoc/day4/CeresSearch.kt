package dev.dc.aoc.day4

import java.io.FileNotFoundException

fun main() {
    val inputFileName = "day4.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val inputLines = inputFile.bufferedReader().readLines()

    part1(inputLines)
    part2(inputLines)
}

private fun part1(lines: List<String>) {
    val padding = "...."
    val cols = lines.first().length + padding.length
    val text = lines.joinToString(separator = padding)

    val indices = listOf(
        text.indices.map { it until it + 4 }, // horizontal
        text.indices.map { it until it + 4 * cols step cols }, // vertical
        text.indices.map { it until it + 4 * (cols + 1) step cols + 1 }, // forward diagonal
        text.indices.map { it until it + 4 * (cols - 1) step cols - 1 }, // backward diagonal
    ).flatten()

    val regex = "XMAS|SAMX".toRegex()
    val count = indices
        .filterNot { it.last >= text.length }
        .map { text.slice(it) }
        .count { regex.containsMatchIn(it) }

    println("Part 1: $count")
}

private fun part2(lines: List<String>) {
    val columns = lines.first().length
    val text = lines.joinToString(separator = "")

    val indices = text.indices
        .filter { it > columns && it % columns in 1 until columns - 1 && it < text.length - columns }
        .map {
            Pair(
                (it - columns - 1..it + (columns + 1) step columns + 1), // forward diagonal
                (it - columns + 1..it + (columns - 1) step columns - 1) // backward diagonal
            )
        }

    val regex = "MAS|SAM".toRegex()
    val count = indices
        .count { (forward, backward) ->
            regex.containsMatchIn(text.slice(forward)) && regex.containsMatchIn(text.slice(backward))
        }

    println("Part 2: $count")
}
