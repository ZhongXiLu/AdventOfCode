package aoc.years.year2022

import aoc.Day

@Year2022
class Day12 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val hill = input.map { it.toCharArray() }

        return hill.findPosition('S')
            .map { startingPosition -> hill.stepsToEnd(startingPosition) }
            .first()
    }

    override fun solvePart2(input: List<String>): Any {
        val hill = input.map { it.toCharArray() }

        return hill.findPosition('a')
            .minOf { startingPosition -> hill.stepsToEnd(startingPosition) }
    }

}

private fun List<CharArray>.findPosition(startingPositionSymbol: Char): List<Pair<Int, Int>> {
    return this
        .indices
        .map { row ->
            this.first().indices
                .filter { column -> this[row][column] == startingPositionSymbol }
                .map { column -> Pair(row, column) }
        }
        .flatten()
}

private fun List<CharArray>.stepsToEnd(startingPosition: Pair<Int, Int>): Int {
    val visited = this.indices
        .map {
            this.first().indices
                .map { Pair(false, Integer.MAX_VALUE) }
                .toMutableList()
        }

    val nextSquares = mutableListOf(startingPosition)
    visited[startingPosition.first][startingPosition.second] = Pair(false, 0)

    while (nextSquares.isNotEmpty()) {
        val currentSquarePos = nextSquares.removeFirst()

        val (currentSquareVisited, currentSquareDistance) = visited[currentSquarePos.first][currentSquarePos.second]
        if (!currentSquareVisited) {
            visited[currentSquarePos.first][currentSquarePos.second] = Pair(true, currentSquareDistance)

            if (currentSquarePos.first - 1 >= 0) {
                val nextSquare = Pair(currentSquarePos.first - 1, currentSquarePos.second)
                visitSquare(currentSquarePos, currentSquareDistance, nextSquare, nextSquares, visited)
            }
            if (currentSquarePos.first + 1 < this.size) {
                val nextSquare = Pair(currentSquarePos.first + 1, currentSquarePos.second)
                visitSquare(currentSquarePos, currentSquareDistance, nextSquare, nextSquares, visited)
            }
            if (currentSquarePos.second - 1 >= 0) {
                val nextSquare = Pair(currentSquarePos.first, currentSquarePos.second - 1)
                visitSquare(currentSquarePos, currentSquareDistance, nextSquare, nextSquares, visited)
            }
            if (currentSquarePos.second + 1 < this.first().size) {
                val nextSquare = Pair(currentSquarePos.first, currentSquarePos.second + 1)
                visitSquare(currentSquarePos, currentSquareDistance, nextSquare, nextSquares, visited)
            }
        }
    }

    val endPosition = findPosition('E').first()
    return visited[endPosition.first][endPosition.second].second
}

private fun List<CharArray>.visitSquare(
    currentSquarePos: Pair<Int, Int>,
    currentSquareDistance: Int,
    nextSquarePos: Pair<Int, Int>,
    nextSquares: MutableList<Pair<Int, Int>>,
    visited: List<MutableList<Pair<Boolean, Int>>>
) {
    if (atMostOneStepHigher(currentSquarePos, nextSquarePos)) {
        nextSquares.add(nextSquarePos)
        val nextSquare = visited[nextSquarePos.first][nextSquarePos.second]
        val distanceToNextSquare = nextSquare.second
        if (currentSquareDistance + 1 < distanceToNextSquare) {
            visited[nextSquarePos.first][nextSquarePos.second] = Pair(nextSquare.first, currentSquareDistance + 1)
        }
    }
}

private fun List<CharArray>.atMostOneStepHigher(current: Pair<Int, Int>, next: Pair<Int, Int>): Boolean {
    return if (this[current.first][current.second] == 'S') {
        this[next.first][next.second] == 'a'
    } else if (this[next.first][next.second] == 'E') {
        true
    } else {
        this[next.first][next.second].minus(this[current.first][current.second]) <= 1
    }
}
