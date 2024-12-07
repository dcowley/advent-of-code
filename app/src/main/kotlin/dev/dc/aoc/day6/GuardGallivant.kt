package dev.dc.aoc.day6

import java.io.FileNotFoundException

fun main() {
    val inputFileName = "day6-eg.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val input = inputFile.bufferedReader().readLines()

    val width = input.first().length
    val map = input.joinToString("")

    var direction = Direction.UP
    var position = map.indexOf('^')

    fun nextPosition(position: Int, direction: Direction) = when (direction) {
        Direction.UP -> position - width
        Direction.RIGHT -> position + 1
        Direction.DOWN -> position + width
        Direction.LEFT -> position - 1
    }

    val path = mutableListOf(position to direction)
    var nextPosition = nextPosition(position, direction)
    while (nextPosition in map.indices) {
        if (map[nextPosition] != '#') {
            path.add(nextPosition to direction)
            position = nextPosition
        } else {
            direction = direction.next()
        }
        nextPosition = nextPosition(position, direction)
    }

//    println(path.map { it.first }.toSet().size)

    val blockages = mutableMapOf<Int, Direction>()
//    path.forEach { movement ->
//        direction = Direction.UP
//        position = map.indexOf('^')
//        nextPosition = nextPosition(position, direction)
//
//        val blockedMap = nextPosition(movement.first, movement.second).let {
//            map.replaceRange(it..it, "O")
//        }
//
//        val newPath = mutableListOf(position to direction)
//        while (nextPosition in map.indices) {
//            if (blockedMap[nextPosition] == '#' || blockedMap[nextPosition] == 'O') {
//                if (newPath.any { it.first == nextPosition(position, direction.next()) && it.second == direction.next() }) {
//                    println("Cycle!")
//                    blockages[position] = direction.next()
//                    break
//                }
//                direction = direction.next()
//            } else {
//                newPath.add(nextPosition to direction)
//                position = nextPosition
//            }
//            nextPosition = nextPosition(position, direction)
//        }
//    }

    crawler()
}

private fun crawler() {
    val inputFileName = "day6-eg.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val input = inputFile.bufferedReader().readLines()

    val width = input.first().length
    val map = input.joinToString("")

    var position = map.indexOf('^')
    var direction = Direction.UP

    fun nextPosition(position: Int, direction: Direction) = when (direction) {
        Direction.UP -> position - width
        Direction.RIGHT -> position + 1
        Direction.DOWN -> position + width
        Direction.LEFT -> position - 1
    }

    val path = mutableListOf<Pair<Int, Direction>>()
    while (true) {
        val nextPosition = nextPosition(position, direction)
        if (nextPosition !in map.indices) {
            break
        }

        if (map[nextPosition] == '#') {
            direction = direction.next()
            path.add(position to direction)
        } else {
            position = nextPosition
            path.add(nextPosition to direction)
        }
    }

    println(path.distinctBy { it.first }.size)

    val options = path.map { it.first }.toSet()
    val obstacles = mutableSetOf<Int>()
    options.forEach { obstacle ->
        position = map.indexOf('^')
        direction = Direction.UP
        path.clear()

        while (true) {
            val nextPosition = nextPosition(position, direction)
            if (nextPosition !in map.indices) {
                break
            }
            if (nextPosition to direction in path) {
                println("Cycle!")
                break
            }

            if (map[nextPosition] == '#') {
                direction = direction.next()
                path.add(position to direction)
            } else if (nextPosition == obstacle) {
                direction = direction.next()
                if (path.contains(position to direction)) {
                    obstacles.add(obstacle)
                    break
                }
                path.add(position to direction)
            } else {
                position = nextPosition
                path.add(nextPosition to direction)
            }
        }
    }
    println(obstacles.size)
}

private fun part2() {
    val inputFileName = "day6-eg.txt"
    val inputFile = ClassLoader.getSystemResourceAsStream(inputFileName) ?: throw FileNotFoundException(inputFileName)
    val input = inputFile.bufferedReader().readLines()

    val width = input.first().length
    val map = input.joinToString("")

    var direction = Direction.UP
    var position = map.indexOf('^')

    fun nextPosition(position: Int, direction: Direction) = when (direction) {
        Direction.UP -> position - width
        Direction.RIGHT -> position + 1
        Direction.DOWN -> position + width
        Direction.LEFT -> position - 1
    }

    var nextPosition = nextPosition(position, direction)

    val path = mutableListOf(position to direction)
    path.forEachIndexed { index, movement ->
        println("Step: ${movement.first} in direction ${movement.second}")

        val next = nextPosition(movement.first, movement.second)
        val blockedMap = map.replaceRange(next..next, "O")

        while (nextPosition in blockedMap.indices) {
            if (map[nextPosition] != '#') {
                path.add(nextPosition to direction)
                position = nextPosition
            } else {
                direction = direction.next()
            }
            nextPosition = nextPosition(position, direction)
        }

        val hasVisited = path.slice(0 until index).any { it.first == movement.first }
        if (hasVisited) {
            println("has visited")
        }
    }
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
