package aoc.years.year2024

import aoc.Day

@Year2024
class Day04 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val wordSearch = WordSearch.of(input)
        return wordSearch.countXmas()
    }

    override fun solvePart2(input: List<String>): Any {
        return input
    }

}

private class WordSearch(val grid: List<List<Char>>) {
    companion object {
        fun of(input: List<String>): WordSearch {
            val grid = input.map { it.map { char -> char } }
            return WordSearch(grid)
        }
    }

    fun countXmas(): Int {
        var count = 0

        grid.indices.forEach { row ->
            grid[row].indices.forEach { column ->
                if (grid[row][column] == 'X') {
                    count += countXmas(row, column)
                }
            }
        }

        return count
    }


    private fun countXmas(row: Int, column: Int): Int {
        var count = 0

        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)))
        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(0, -1), Pair(0, -2), Pair(0, -3)))
        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)))
        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(-1, 0), Pair(-2, 0), Pair(-3, 0)))

        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2), Pair(3, 3)))
        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(-1, -1), Pair(-2, -2), Pair(-3, -3)))
        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(1, -1), Pair(2, -2), Pair(3, -3)))
        count += countXmasInAllDirections2(row, column, listOf(Pair(0, 0), Pair(-1, 1), Pair(-2, 2), Pair(-3, 3)))

        return count
    }

    private fun countXmasInAllDirections2(row: Int, column: Int, positions: List<Pair<Int, Int>>): Int {
        var isXmas = 0
        positions.forEachIndexed { index, position ->
            if (index == 0 && row + position.first in grid.indices && column + position.second in grid.first().indices  && grid[row + position.first][column + position.second] == 'X') {
                isXmas++
            }
            if (index == 1 && row + position.first in grid.indices && column + position.second in grid.first().indices  && grid[row + position.first][column + position.second] == 'M') {
                isXmas++
            }
            if (index == 2 && row + position.first in grid.indices && column + position.second in grid.first().indices  && grid[row + position.first][column + position.second] == 'A') {
                isXmas++
            }
            if (index == 3 && row + position.first in grid.indices && column + position.second in grid.first().indices  && grid[row + position.first][column + position.second] == 'S') {
                isXmas++
            }
        }
        return if (isXmas == 4) 1 else 0
    }
}