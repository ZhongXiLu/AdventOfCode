package aoc.years.year2022

import aoc.Day

@Year2022
class Day14 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val (width, height) = getCaveDimensions(input)
        val cave = (0..width).map { (0..height).map { "." }.toMutableList() }.toMutableList()
        input.forEach { cave.placeRock(it) }

        return (0..(width * height))
            .takeWhile { cave.pourSand(Pair(500, 0)) }
            .count()
    }

    override fun solvePart2(input: List<String>): Any {
        val (width, height) = getCaveDimensions(input)
        val cave = (0..width * 2).map { (0..height + 2).map { "." }.toMutableList() }.toMutableList()
        input.forEach { cave.placeRock(it) }

        cave.placeRock("0,${height + 2} -> ${width * 2},${height + 2}")     // "infinite line"

        return (0..(width * height))
            .takeWhile {
                if (cave[500][0] != "o") {
                    cave[500][0] = "o"
                    cave.pourSand(Pair(500, 0))
                    true
                } else {
                    false
                }
            }
            .count()
    }

}

private fun getCaveDimensions(input: List<String>): Pair<Int, Int> {
    val coordinates = input
        .map { it.split(" -> ") }
        .flatten()
    val width = coordinates.maxOf { it.split(",")[0].toInt() }
    val height = coordinates.maxOf { it.split(",")[1].toInt() }
    return Pair(width, height)
}

private fun MutableList<MutableList<String>>.pourSand(sand: Pair<Int, Int>): Boolean {
    if (sand.second + 1 >= this.first().size || sand.first + 1 >= this.size) {
        return false    // reached the abyss
    }

    if (this[sand.first][sand.second + 1] == ".") {
        return dropSand(sand.first, sand.second, sand.first, sand.second + 1)
    } else if (this[sand.first - 1][sand.second + 1] == ".") {
        return dropSand(sand.first, sand.second, sand.first - 1, sand.second + 1)
    } else if (this[sand.first + 1][sand.second + 1] == ".") {
        return dropSand(sand.first, sand.second, sand.first + 1, sand.second + 1)
    }

    return true     // "comes to rest"
}

private fun MutableList<MutableList<String>>.dropSand(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    this[x1][y1] = "."
    this[x2][y2] = "o"
    return pourSand(Pair(x2, y2))
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
