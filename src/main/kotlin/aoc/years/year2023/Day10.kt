package aoc.years.year2023

import aoc.Day

@Year2023
class Day10 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Map.of(input).getFarthestDistanceFromStart()
    }

    override fun solvePart2(input: List<String>): Any {
        return Map.of(input).getNrOfEnclosedTiles()
    }

}

private data class Map(var map: List<CharArray>) {
    companion object {
        fun of(input: List<String>): Map {
            return Map(input.map { it.toCharArray() })
        }
    }

    fun getFarthestDistanceFromStart(): Int {
        val visited = traverseLoop()
        return visited.maxOf { row -> row.maxOf { it.second } }
    }

    // Dijkstra
    private fun traverseLoop(): List<MutableList<Pair<Boolean, Int>>> {
        val visited = this.map.indices
            .map {
                this.map.first().indices
                    .map { Pair(false, -1) }
                    .toMutableList()
            }

        val startingPosition = getStartingPosition()
        val next = mutableListOf(startingPosition)
        visited[startingPosition.first][startingPosition.second] = Pair(false, 0)

        while (next.isNotEmpty()) {
            val current = next.removeFirst()

            val (currentVisited, currentDistance) = visited[current.first][current.second]
            if (!currentVisited) {
                visited[current.first][current.second] = Pair(true, currentDistance)

                this.map.getConnectedPipes(current)
                    .filter { it.first in this.map.indices && it.second in this.map.first().indices }
                    .forEach {
                        next.add(it)
                        val nextSquare = visited[it.first][it.second]
                        val distanceToNextSquare = nextSquare.second
                        if (currentDistance + 1 < distanceToNextSquare || distanceToNextSquare == -1) {
                            visited[it.first][it.second] = Pair(false, currentDistance + 1)
                        }
                    }
            }
        }

        return visited
    }

    private fun getStartingPosition(): Pair<Int, Int> {
        map.forEachIndexed { x, row ->
            row.forEachIndexed { y, char ->
                if (char == 'S') {
                    return Pair(x, y)
                }
            }
        }
        return Pair(-1, -1)
    }

    fun getNrOfEnclosedTiles(): Int {
        val visited = traverseLoop()
        return getNonLoopTiles(visited).count { this.isEnclosed(it, visited) }
    }

    private fun getNonLoopTiles(visited: List<MutableList<Pair<Boolean, Int>>>): List<Pair<Int, Int>> {
        return visited.mapIndexed { x, row ->
            row.mapIndexed { y, tile -> if (tile.first) null else Pair(x, y) }
        }.flatten().filterNotNull()
    }

    private fun isEnclosed(pos: Pair<Int, Int>, visited: List<MutableList<Pair<Boolean, Int>>>): Boolean {
        val leftPipes = (0..pos.second).count { visited[pos.first][it].first && isVerticalPipe(this.map[pos.first][it]) }
        return if (leftPipes % 2 != 0) true else false
    }

    private fun isVerticalPipe(pipe: Char): Boolean {
        return pipe == '|' || pipe == 'L' || pipe == 'J' || pipe == 'S'
    }

}

private fun List<CharArray>.getConnectedPipes(pos: Pair<Int, Int>): List<Pair<Int, Int>> {
    return when (val pipe = this[pos.first][pos.second]) {
        'S' -> listOf(Pair(pos.first - 1, pos.second), Pair(pos.first, pos.second + 1))      // hard coded :)
//        'S' -> listOf(Pair(pos.first, pos.second + 1), Pair(pos.first + 1, pos.second))

        '|' -> listOf(Pair(pos.first - 1, pos.second), Pair(pos.first + 1, pos.second))
        '-' -> listOf(Pair(pos.first, pos.second - 1), Pair(pos.first, pos.second + 1))
        'L' -> listOf(Pair(pos.first - 1, pos.second), Pair(pos.first, pos.second + 1))
        'J' -> listOf(Pair(pos.first - 1, pos.second), Pair(pos.first, pos.second - 1))
        '7' -> listOf(Pair(pos.first, pos.second - 1), Pair(pos.first + 1, pos.second))
        'F' -> listOf(Pair(pos.first, pos.second + 1), Pair(pos.first + 1, pos.second))
        '.' -> listOf()
        else -> throw Exception("Unknown character: $pipe")
    }
}
