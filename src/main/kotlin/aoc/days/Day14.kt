package aoc.days

import aoc.Day

private const val CAVE_HEIGHT = 200
private const val CAVE_WIDTH = 600

class Day14 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val cave = (0..CAVE_WIDTH).map { (0..CAVE_HEIGHT).map { "." }.toMutableList() }.toMutableList()
        input.forEach { cave.placeRock(it) }

        return (0..(CAVE_WIDTH * CAVE_HEIGHT))
            .takeWhile { cave.pourSand(Pair(500, 0)) }
            .count()
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private fun MutableList<MutableList<String>>.pourSand(sand: Pair<Int, Int>): Boolean {
    if (sand.second + 1 > CAVE_HEIGHT || sand.first + 1 > CAVE_WIDTH) {
        return false
    }

    if (this[sand.first][sand.second + 1] == ".") {
        this[sand.first][sand.second] = "."
        this[sand.first][sand.second + 1] = "+"
        return pourSand(Pair(sand.first, sand.second + 1))

    } else if (this[sand.first - 1][sand.second + 1] == ".") {
        this[sand.first][sand.second] = "."
        this[sand.first - 1][sand.second + 1] = "+"
        return pourSand(Pair(sand.first - 1, sand.second + 1))

    } else if (this[sand.first + 1][sand.second + 1] == ".") {
        this[sand.first][sand.second] = "."
        this[sand.first + 1][sand.second + 1] = "+"
        return pourSand(Pair(sand.first + 1, sand.second + 1))
    }

    return true
}

private fun MutableList<MutableList<String>>.placeRock(rockPath: String) {
    rockPath
        .split(" -> ")
        .map {
            val split = it.split(",")
            Pair(split[0].toInt(), split[1].toInt())
        }
        .zipWithNext()
        .forEach { (c1, c2) ->
            val (x1, y1) = c1
            val (x2, y2) = c2
            val xRange = if (x1 < x2) x1..x2 else x2..x1
            val yRange = if (y1 < y2) y1..y2 else y2..y1
            xRange.forEach { x ->
                yRange.forEach { y ->
                    this[x][y] = "#"
                }
            }
        }
}
