package aoc.years.year2024

import aoc.Day

@Year2024
class Day10 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return TrailMap.of(input).getTrailheadScore()
    }

    override fun solvePart2(input: List<String>): Any {
        return TrailMap.of(input).getTrailheadRating()
    }
}

private class TrailMap(val map: List<List<Int>>) {
    companion object {
        fun of(input: List<String>): TrailMap {
            val map = input.map { it.map(Char::digitToInt) }
            return TrailMap(map)
        }
    }

    fun getTrailheadScore(): Int {
        return getTrailheads().sumOf { it.getTrailends().size }
    }

    private fun Pair<Int, Int>.getTrailends(): Set<Pair<Int, Int>> {
        if (map[this.first][this.second] == 9) {
            return setOf(this)
        }

        return this.getSurroundingPath().map { it.getTrailends() }.flatten().toSet()
    }

    fun getTrailheadRating(): Int {
        return getTrailheads().sumOf { it.getTrailPaths() }
    }

    private fun Pair<Int, Int>.getTrailPaths(): Int {
        if (map[this.first][this.second] == 9) {
            return 1
        }

        return this.getSurroundingPath().sumOf { it.getTrailPaths() }
    }


    private fun getTrailheads(): List<Pair<Int, Int>> {
        val trailheads = mutableListOf<Pair<Int, Int>>()
        for (x in map.indices) {
            for (y in map[x].indices) {
                if (map[x][y] == 0) {
                    trailheads.add(Pair(x, y))
                }
            }
        }
        return trailheads
    }

    private fun Pair<Int, Int>.getSurroundingPath(): List<Pair<Int, Int>> {
        return listOf(Pair(first + 1, second), Pair(first - 1, second), Pair(first, second + 1), Pair(first, second - 1))
            .filter { it.first in map.indices && it.second in map[it.first].indices }
            .filter { map[it.first][it.second] == map[first][second] + 1 }
    }
}
