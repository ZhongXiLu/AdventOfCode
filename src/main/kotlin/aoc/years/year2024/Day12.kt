package aoc.years.year2024

import aoc.Day

@Year2024
class Day12 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Garden.of(input).getTotalFencePrice()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private class Garden(val garden: List<List<Char>>) {
    companion object {
        fun of(input: List<String>): Garden {
            val grid = input.map { it.map { char -> char } }
            return Garden(grid)
        }
    }

    fun getTotalFencePrice(): Int {
        var totalFencePrice = 0

        val visitedGardens = mutableSetOf<Set<Pair<Int, Int>>>()
        for (x in garden.indices) {
            for (y in garden[x].indices) {
                val region = getRegion(Pair(x, y))
                if (!visitedGardens.contains(region)) {
                    visitedGardens.add(region)
                    totalFencePrice += (region.size * region.getPerimeterSize())
                }
            }
        }

        return totalFencePrice
    }

    private fun getRegion(plot: Pair<Int, Int>): Set<Pair<Int, Int>> {
        val next = mutableListOf(plot)
        val visited = mutableSetOf<Pair<Int, Int>>()

        while (next.isNotEmpty()) {
            val current = next.removeFirst()

            if (visited.contains(current)) {
                continue
            }
            visited.add(current)

            next.addAll(current.getSameSurroundingArea())
        }

        return visited
    }

    private fun Pair<Int, Int>.getSameSurroundingArea(): List<Pair<Int, Int>> {
        return listOf(Pair(first - 1, second), Pair(first + 1, second), Pair(first, second - 1), Pair(first, second + 1))
            .filter { it.first in garden.indices && it.second in garden[it.first].indices }
            .filter { garden[it.first][it.second] == garden[this.first][this.second] }
    }

    private fun Set<Pair<Int, Int>>.getPerimeterSize(): Int {
        return this.sumOf { it.getDifferentSurroundingArea().size }
    }

    private fun Pair<Int, Int>.getDifferentSurroundingArea(): List<Pair<Int, Int>> {
        return listOf(Pair(first - 1, second), Pair(first + 1, second), Pair(first, second - 1), Pair(first, second + 1))
            .filter { it.first !in garden.indices || it.second !in garden[it.first].indices || garden[it.first][it.second] != garden[this.first][this.second] }
    }

}

