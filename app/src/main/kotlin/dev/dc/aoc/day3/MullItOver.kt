package dev.dc.aoc.day3

import java.io.FileNotFoundException

fun main() {
    val inputFileName = "day3.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val input = inputFile.bufferedReader().readText()

    val isMultiply = "mul\\((\\d+),(\\d+)\\)".toRegex()
    val part1 = isMultiply.findAll(input)
        .fold(0) { acc, match ->
            val l = match.groups[1]?.value?.toInt() ?: 0
            val r = match.groups[2]?.value?.toInt() ?: 0
            acc + (l * r)
        }
    println("Part 1: $part1")
}
