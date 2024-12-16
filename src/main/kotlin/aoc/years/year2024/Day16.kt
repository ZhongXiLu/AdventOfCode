package aoc.years.year2024

import aoc.Day

@Year2024
class Day16 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Maze.of(input).findShortestPath()
    }

    override fun solvePart2(input: List<String>): Any {
        return Maze.of(input).findShortestPathLength()
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

    fun findShortestPathLength(): Int {
        val visited = traverse()

        val pathLength = visited.getPathLength(getEnd())
        // print the maze
        maze.forEachIndexed { x, row ->
            row.forEachIndexed { y, c ->
//                print(visited[x][y].toString() + "\t")
                if (Pair(x, y) in pathLength) {
                    print("O")
                } else {
                    print(c)
                }
            }
            println()
        }

        return pathLength.size
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

    private fun List<MutableList<Triple<Boolean, Int, Char>>>.getPathLength(position: Pair<Int, Int>): List<Pair<Int, Int>> {
        if (maze[position.first][position.second] == 'S') {
            return listOf(position)
        }

        val (_, distance, direction) = this[position.first][position.second]
        val paths = Triple(position.first, position.second, direction).getNeighbours()
            .filter {
                var distanceOfNeighbour = distance
                if (this[it.first][it.second].third == it.third.flip() && this[it.first][it.second].third == direction) {
                    distanceOfNeighbour -= 1
                } else if (this[it.first][it.second].third == it.third.flip()) {
                    distanceOfNeighbour += 999
                } else {
                    distanceOfNeighbour -= 1001
                }
                return@filter this[it.first][it.second].second == distanceOfNeighbour
            }
            .map { getPathLength(Pair(it.first, it.second)) }
            .flatten()

        return listOf(position).plus(paths).distinct()
    }

    private fun Char.flip(): Char {
        return when (this) {
            '^' -> 'v'
            'v' -> '^'
            '<' -> '>'
            '>' -> '<'
            else -> throw IllegalStateException("Not a valid direction: $this")
        }
    }
}
