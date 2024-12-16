package aoc.years.year2024

import aoc.Day

@Year2024
class Day16 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Maze.of(input).findShortestPath()
    }

    override fun solvePart2(input: List<String>): Any {
        return 1
    }

}

class Maze(private val maze: List<List<Char>>) {
    companion object {
        fun of(input: List<String>): Maze {
            val maze = input.map { it.map { char -> char } }
            return Maze(maze)
        }
    }

    fun findShortestPath(): Int {
        val visited = traverse()
        val (x, y) = getEnd()
        return visited[x][y].second
    }

    private fun traverse(): List<MutableList<Triple<Boolean, Int, Char>>> {
        val visited = maze.indices
            .map {
                maze.first().indices
                    .map { Triple(false, Integer.MAX_VALUE, '>') }
                    .toMutableList()
            }
        val startingPosition = getStart()
        val next = mutableListOf(Triple(startingPosition.first, startingPosition.second, '>'))
        visited[startingPosition.first][startingPosition.second] = Triple(false, 0, '>')

        while (next.isNotEmpty()) {
            val current = next.removeFirst()

            val (currentVisited, currentDistance, currentDirection) = visited[current.first][current.second]
            if (!currentVisited) {
                visited[current.first][current.second] = Triple(true, currentDistance, currentDirection)

                current.getNeighbours().forEach {
                    next.add(it)
                    val nextPosition = visited[it.first][it.second]
                    val distanceToNextSquare = nextPosition.second

                    val currentDistanceToNextSquare = if (it.third != currentDirection) currentDistance + 1001 else currentDistance + 1

                    if (currentDistanceToNextSquare < distanceToNextSquare) {
                        visited[it.first][it.second] = Triple(false, currentDistanceToNextSquare, it.third)
                    }
                }
            }
        }


        return visited
    }

    private fun Triple<Int, Int, Char>.getNeighbours(): List<Triple<Int, Int, Char>> {
        return listOf(
            Triple(this.first + 1, this.second, 'v'),
            Triple(this.first - 1, this.second, '^'),
            Triple(this.first, this.second + 1, '>'),
            Triple(this.first, this.second - 1, '<')
        )
            .filter { it.first in maze.indices && it.second in maze.first().indices }
            .filter { maze[it.first][it.second] != '#' }
    }

    private fun getStart(): Pair<Int, Int> {
        return find('S')
    }

    private fun getEnd(): Pair<Int, Int> {
        return find('E')
    }

    private fun find(char: Char): Pair<Int, Int> {
        maze.withIndex().forEach { (x, row) ->
            row.withIndex().forEach { (y, c) ->
                if (c == char) {
                    return Pair(x, y)
                }
            }
        }
        throw IllegalStateException("Not found: $char")
    }

}
