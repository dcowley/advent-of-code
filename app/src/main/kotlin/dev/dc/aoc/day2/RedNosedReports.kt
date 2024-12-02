package dev.dc.aoc.day2

import java.io.FileNotFoundException

fun main() {
    val inputFileName = "levels.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val inputLines = inputFile.bufferedReader().readLines()

    val levels = inputLines.map { it.split(' ').map(String::toInt) }
    val isSafe = levels.count { isSafe(it) || isSafe(it.reversed()) }
    val isSafeWithDampener = levels.count { isSafeWithDampener(it) || isSafeWithDampener(it.reversed()) }

    println("Safe levels: $isSafe")
    println("Safe levels with dampener: $isSafeWithDampener")
}

fun isSafe(levels: List<Int>): Boolean {
    val diff = IntArray(levels.size - 1) { i ->
        levels[i + 1] - levels[i]
    }
    return diff.all { it in 1..3 }
}

fun isSafeWithDampener(levels: List<Int>): Boolean {
    return levels.indices.any { index ->
        isSafe(levels.toMutableList().also {
            it.removeAt(index)
        })
    }
}
