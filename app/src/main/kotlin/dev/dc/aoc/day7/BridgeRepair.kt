package dev.dc.aoc.day7

import java.io.FileNotFoundException

fun main() {
    part1()
}

fun part1() {
    val inputFileName = "puzzles/7"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val input = inputFile.bufferedReader().readLines()

    val results = input.map { it.substringBefore(':').toLong() }
    val numbers = input.map { it.substringAfter(": ").split(' ').map(String::toLong) }

    var solution = 0L
    numbers.forEachIndexed { i, ints ->
        val operatorPermutations = permutations(charArrayOf('+', '*'), ints.size - 1)
        val hasSolution = operatorPermutations.any { permutation ->
            val result = ints.reduceIndexed { index, acc, next ->
                when (permutation[index - 1]) {
                    '+' -> acc + next
                    '*' -> acc * next
                    else -> throw UnsupportedOperationException("Invalid operator ${permutation[index]}")
                }
            }
            results[i] == result
        }
        if (hasSolution) {
            solution += results[i]
        }
    }
    println(solution)
}

fun permutations(chars: CharArray, length: Int): List<CharArray> {
    return when (length) {
        1 -> chars.map { charArrayOf(it) }
        else -> chars.flatMap { char ->
            permutations(chars, length - 1).map { it + char }
        }
    }
}
