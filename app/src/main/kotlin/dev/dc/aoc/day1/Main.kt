package dev.dc.aoc.day1

import java.io.FileNotFoundException
import kotlin.math.absoluteValue


fun main() {
    val inputFileName = "location_ids.csv"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val inputLines = inputFile.bufferedReader().readLines()

    totalDistance(inputLines)
}

fun totalDistance(inputLines: List<String>) {
    val locationIds1 = mutableListOf<Int>()
    val locationIds2 = mutableListOf<Int>()
    inputLines.forEach {
        locationIds1.sortedInsert(it.substringBefore(',').toInt())
        locationIds2.sortedInsert(it.substringAfter(',').toInt())
    }

    val totalDistance = locationIds1
        .zip(locationIds2)
        .fold(0) { acc, pair ->
            acc + (pair.first - pair.second).absoluteValue
        }

    println("The total distance between both lists is: $totalDistance")
}

fun MutableList<Int>.sortedInsert(element: Int) {
    val insertIndex = binarySearch(element)
        .let {
            if (it >= 0) it else -it - 1
        }
    add(insertIndex, element)
}
