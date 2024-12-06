package dev.dc.aoc.day6

import java.io.FileNotFoundException

fun main() {
    val inputFileName = "day6.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val input = inputFile.bufferedReader().readLines()

    val width = input.first().length
    val map = input.joinToString("")

    var direction = Direction.UP
    var position = map.indexOf('^')

    fun nextPosition(position: Int) = when (direction) {
        Direction.UP -> position - width
        Direction.RIGHT -> position + 1
        Direction.DOWN -> position + width
        Direction.LEFT -> position - 1
    }

    val path = mutableSetOf<Int>()
    var nextPosition = nextPosition(position)
    while (nextPosition in map.indices) {
        if (map[nextPosition] != '#') {
            path.add(nextPosition)
            position = nextPosition
        } else {
            direction = direction.next()
        }
        nextPosition = nextPosition(position)
    }

    println(path.size)
}

enum class Direction {
    UP, RIGHT, DOWN, LEFT;

    fun next() = when (this) {
        UP -> RIGHT
        RIGHT -> DOWN
        DOWN -> LEFT
        LEFT -> UP
    }
}
