package dev.dc.aoc.day5

import java.io.FileNotFoundException

fun main() {
    val inputFileName = "day5.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val input = inputFile.bufferedReader().readText()

    val rules = "(\\d+)\\|(\\d+)".toRegex()
        .findAll(input)
        .map { it.groups[1]!!.value.toInt() to it.groups[2]!!.value.toInt() }
        .toMutableList()

    val updates = "\\d+(?:,+\\d+)+".toRegex()
        .findAll(input)
        .map {
            it.groups.first()!!.value.split(',').map(String::toInt).toIntArray()
        }
        .toList()

    val part1 = updates.fold(0) { acc, update ->
        val isValid = update.all { i ->
            rules
                .filter { it.first == i }
                .none { it.second in update.slice(0..update.indexOf(i)) }
        }
        if (isValid) {
            acc + update[update.size / 2]
        } else {
            acc
        }
    }
    println(part1)
}
