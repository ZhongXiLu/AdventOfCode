package aoc.years.year2024

import aoc.Day

@Year2024
class Day06 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val map = GuardMap.of(input)
        map.traverse(map.getStartingPosition())
        return map.countPath()
    }

    override fun solvePart2(input: List<String>): Any {
        val map = GuardMap.of(input)
        return map.traverseWithObstructionPlacement(map.getStartingPosition())
    }

}

private class GuardMap(val map: MutableList<MutableList<Char>>) {
    companion object {
        fun of(input: List<String>): GuardMap {
            val map = input.map { it.map { char -> char }.toMutableList() }.toMutableList()
            return GuardMap(map)
        }
    }

    fun getStartingPosition(): Pair<Int, Int> {
        map.indices.forEach { x ->
            map[x].indices.forEach { y ->
                if (map[x][y] == '^') {
                    return Pair(x, y)
                }
            }
        }
        throw IllegalStateException("No starting position found")
    }

    fun traverse(startingPosition: Pair<Int, Int>) {
        val (x, y) = startingPosition

        val (newX, newY) = getDirection(map[x][y])
        if (x + newX !in map.indices || y + newY !in map[x].indices) {
            map[x][y] = 'X'
            return

        } else if (map[x + newX][y + newY] == '#') {
            map[x][y] = rotate(map[x][y])
            traverse(Pair(x, y))

        } else {
            map[x + newX][y + newY] = map[x][y]
            map[x][y] = 'X'
            traverse(Pair(x + newX, y + newY))
        }
    }

    fun traverseWithObstructionPlacement(startingPosition: Pair<Int, Int>): Int {
        var count = 0

        val (x, y) = startingPosition
        val (newX, newY) = getDirection(map[x][y])

        if (x + newX !in map.indices || y + newY !in map[x].indices) {
            return count
        }

        if (map[x + newX][y + newY] == '#') {
            map[x][y] = rotate(map[x][y])
            count += traverseWithObstructionPlacement(Pair(x, y))

        } else {
            val mapCopy = map.map { it.toMutableList() }.toMutableList()
            mapCopy[x + newX][y + newY] = '#'
            val initialDirection = mapCopy[x][y]
            mapCopy[x][y] = rotate(map[x][y])
            if (GuardMap(mapCopy).isLoopFrom(Pair(x, y), Pair(x, y), initialDirection, 0)) count++

            map[x + newX][y + newY] = map[x][y]
            count += traverseWithObstructionPlacement(Pair(x + newX, y + newY))
        }

        return count
    }

    private fun getDirection(direction: Char): Pair<Int, Int> {
        return when (direction) {
            '^' -> Pair(-1, 0)
            'v' -> Pair(1, 0)
            '<' -> Pair(0, -1)
            '>' -> Pair(0, 1)
            else -> throw IllegalStateException("Invalid direction: $direction")
        }
    }

    private fun rotate(direction: Char): Char {
        return when (direction) {
            '^' -> '>'
            'v' -> '<'
            '<' -> '^'
            '>' -> 'v'
            else -> throw IllegalStateException("Invalid direction: $direction")
        }
    }

    private fun isLoopFrom(currentPosition: Pair<Int, Int>, initialPosition: Pair<Int, Int>, initialDirection: Char, hardStop: Int): Boolean {
        // Hard stop to avoid StackOverFlowError; don't ask me how I got this number...
        if (hardStop == 4620) {
            return false
        }

        val (x, y) = currentPosition

        val (newX, newY) = getDirection(map[x][y])
        if (x + newX !in map.indices || y + newY !in map[x].indices) {
            return false

        } else if (x == initialPosition.first && y == initialPosition.second && map[x][y] == initialDirection) {
            return true

        } else if (map[x + newX][y + newY] == '#') {
            map[x][y] = rotate(map[x][y])
            return isLoopFrom(Pair(x, y), initialPosition, initialDirection, hardStop + 1)

        } else {
            map[x + newX][y + newY] = map[x][y]
            return isLoopFrom(Pair(x + newX, y + newY), initialPosition, initialDirection, hardStop + 1)
        }
    }

    fun countPath(): Int {
        return map.sumOf { row -> row.count { it == 'X' } }
    }

    fun print() {
        map.forEach { row -> println(row.joinToString("")) }
    }
}