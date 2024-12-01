package dev.dc.aoc.day1

import java.io.FileNotFoundException
import kotlin.math.absoluteValue

fun main() {
    val inputFileName = "location_ids.csv"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val inputLines = inputFile.bufferedReader().readLines()

    totalDistance(inputLines)
    similarityScore(inputLines)
}

fun totalDistance(inputLines: List<String>) {
    val locationIds1 = mutableListOf<Int>()
    val locationIds2 = mutableListOf<Int>()
    inputLines.forEach {
        val parts = it.split(',')
        locationIds1.sortedInsert(parts[0].toInt())
        locationIds2.sortedInsert(parts[1].toInt())
    }

    val totalDistance = locationIds1
        .zip(locationIds2)
        .fold(initial = 0) { acc, pair ->
            acc + (pair.first - pair.second).absoluteValue
        }

    println("Total distance: $totalDistance")
}

fun MutableList<Int>.sortedInsert(element: Int) {
    val insertIndex = binarySearch(element)
        .let {
            if (it >= 0) it else -it - 1
        }
    add(insertIndex, element)
}

fun similarityScore(inputLines: List<String>) {
    val locationIds = mutableListOf<Int>()
    val frequencies = mutableMapOf<Int, Int>()

    inputLines.forEach {
        val parts = it.split(',')
        locationIds += parts[0].toInt()

        frequencies.compute(parts[1].toInt()) { _, v ->
            (v ?: 0) + 1
        }
    }

    val similarityScore = locationIds.fold(initial = 0) { acc, i ->
        acc + (i * frequencies.getOrDefault(i, 0))
    }
    println("Similarity score: $similarityScore")
}
