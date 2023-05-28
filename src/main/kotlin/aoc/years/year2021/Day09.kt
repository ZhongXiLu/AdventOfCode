package aoc.years.year2021

import aoc.Day

@Year2021
class Day09 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val map = input.map { it.map { digit -> digit.digitToInt() } }

        return map.indices.sumOf { x ->
            map.first().indices.sumOf { y ->
                if (map.isLowPoint(x, y)) map[x][y] + 1 else 0
            }
        }
    }

    override fun solvePart2(input: List<String>): Any {
        val map = input.map { it.map { digit -> digit.digitToInt() } }

        return map.indices
            .asSequence()
            .map { x -> map.first().indices.filter { y -> map.isLowPoint(x, y) }.map { y -> Pair(x, y) } }
            .flatten()
            .map { (x, y) -> map.getBasinCoords(x, y) }
            .map { it.size }
            .sortedDescending()
            .take(3)
            .reduce { acc, i -> acc * i }
    }

}

private fun List<List<Int>>.getBasinCoords(
    x: Int,
    y: Int,
    visited: MutableSet<Pair<Int, Int>> = mutableSetOf()
): List<Pair<Int, Int>> {
    visited.add(Pair(x, y))
    return getAdjacentSquares(x, y)
        .filter { it !in visited }
        .filter { this[it.first][it.second] != 9}
        .also { visited.addAll(it) }
        .fold(mutableListOf(Pair(x, y))) { pairs, pair ->
            pairs.plus(getBasinCoords(pair.first, pair.second, visited)).toMutableList()
        }
}

private fun List<List<Int>>.isLowPoint(x: Int, y: Int): Boolean {
    return getAdjacentSquares(x, y)
        .all { this[it.first][it.second] > this[x][y] }
}

private fun List<List<Int>>.getAdjacentSquares(x: Int, y: Int): List<Pair<Int, Int>> {
    return listOf(Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
        .filter { it.first in this.indices && it.second in this.first().indices }
}

