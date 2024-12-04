package aoc.years.year2024

import aoc.Day

@Year2024
class Day04 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return WordSearch.of(input).getXmasCount()
    }

    override fun solvePart2(input: List<String>): Any {
        return WordSearch.of(input).getXmasCrossCount()
    }

}

private class WordSearch(val grid: List<List<Char>>) {
    companion object {
        fun of(input: List<String>): WordSearch {
            val grid = input.map { it.map { char -> char } }
            return WordSearch(grid)
        }
    }

    fun getXmasCount(): Int {
        return grid.indices.sumOf { row ->
            grid[row].indices.sumOf { column ->
                getXmasCount(row, column)
            }
        }
    }

    private fun getXmasCount(row: Int, column: Int): Int {
        val directions = listOf(
            listOf(Pair(0, 0), Pair(0, 1), Pair(0, 2), Pair(0, 3)),
            listOf(Pair(0, 0), Pair(0, -1), Pair(0, -2), Pair(0, -3)),
            listOf(Pair(0, 0), Pair(1, 0), Pair(2, 0), Pair(3, 0)),
            listOf(Pair(0, 0), Pair(-1, 0), Pair(-2, 0), Pair(-3, 0)),
            listOf(Pair(0, 0), Pair(1, 1), Pair(2, 2), Pair(3, 3)),
            listOf(Pair(0, 0), Pair(-1, -1), Pair(-2, -2), Pair(-3, -3)),
            listOf(Pair(0, 0), Pair(1, -1), Pair(2, -2), Pair(3, -3)),
            listOf(Pair(0, 0), Pair(-1, 1), Pair(-2, 2), Pair(-3, 3))
        )

        return directions.sumOf { isXmasInDirection(row, column, it) }
    }

    private fun isXmasInDirection(row: Int, column: Int, positions: List<Pair<Int, Int>>): Int {
        val xmasLetters = listOf('X', 'M', 'A', 'S')
        val validXmasLetters = positions.withIndex().count { (index, position) ->
            isLetter(row + position.first, column + position.second, xmasLetters[index])
        }
        return if (validXmasLetters == 4) 1 else 0
    }

    fun getXmasCrossCount(): Int {
        return grid.indices.sumOf { row ->
            grid[row].indices.sumOf { column ->
                if (grid[row][column] == 'A') getXmasCrossCount(row, column) else 0
            }
        }
    }

    private fun getXmasCrossCount(row: Int, column: Int): Int {
        val positionsMS = listOf(
            Pair(listOf(Pair(-1, -1), Pair(-1, 1)), listOf(Pair(1, -1), Pair(1, 1))),
            Pair(listOf(Pair(-1, 1), Pair(1, 1)), listOf(Pair(-1, -1), Pair(1, -1))),
            Pair(listOf(Pair(1, 1), Pair(1, -1)), listOf(Pair(-1, -1), Pair(-1, 1))),
            Pair(listOf(Pair(1, -1), Pair(-1, -1)), listOf(Pair(-1, 1), Pair(1, 1)))
        )

        return positionsMS.sumOf { (positionsM, positionsS) ->
            isXmasCross(row, column, positionsM, positionsS)
        }
    }

    private fun isXmasCross(row: Int, column: Int, positionsM: List<Pair<Int, Int>>, positionsS: List<Pair<Int, Int>>): Int {
        val positions = positionsM + positionsS
        val validXmasLetters = positions.count { position ->
            isLetter(row + position.first, column + position.second, if (position in positionsM) 'M' else 'S')
        }
        return if (validXmasLetters == 4) 1 else 0
    }

    private fun isLetter(x: Int, y: Int, letter: Char) = (x in grid.indices && y in grid.first().indices && grid[x][y] == letter)

}