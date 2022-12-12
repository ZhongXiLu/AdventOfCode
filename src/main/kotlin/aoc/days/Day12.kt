package aoc.days

import aoc.Day

class Day12 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val hill = input
            .map { it.toCharArray() }
        val startingPosition = findStartingPositions(hill, 'S').first()

        return stepsToEnd(hill, startingPosition)
    }

    override fun solvePart2(input: List<String>): Any {
        val hill = input
            .map { it.toCharArray() }
        return findStartingPositions(hill, 'a')
            .minOf { startingPosition -> stepsToEnd(hill, startingPosition) }
    }

    private fun findStartingPositions(hill: List<CharArray>, startingPositionSymbol: Char): List<Pair<Int, Int>> {
        return hill
            .indices
            .map { row ->
                hill.first().indices
                    .filter { column -> hill[row][column] == startingPositionSymbol }
                    .map { column -> Pair(row, column) }
            }
            .flatten()
    }

    private fun stepsToEnd(hill: List<CharArray>, startingPosition: Pair<Int, Int>): Int {
        val visited = hill.indices
            .map {
                hill.first().indices
                    .map { Pair(false, 99999) }
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
                    if (atMostOneStepHigher(
                            hill,
                            currentSquarePos,
                            Pair(currentSquarePos.first - 1, currentSquarePos.second)
                        )
                    ) {
                        nextSquares.add(Pair(currentSquarePos.first - 1, currentSquarePos.second))
                        val distanceToNextSquare = visited[currentSquarePos.first - 1][currentSquarePos.second].second
                        if (currentSquareDistance + 1 < distanceToNextSquare) {
                            visited[currentSquarePos.first - 1][currentSquarePos.second] = Pair(
                                visited[currentSquarePos.first - 1][currentSquarePos.second].first,
                                currentSquareDistance + 1
                            )
                        }
                    }
                }
                if (currentSquarePos.first + 1 < hill.size) {
                    if (atMostOneStepHigher(
                            hill,
                            currentSquarePos,
                            Pair(currentSquarePos.first + 1, currentSquarePos.second)
                        )
                    ) {
                        nextSquares.add(Pair(currentSquarePos.first + 1, currentSquarePos.second))
                        val distanceToNextSquare = visited[currentSquarePos.first + 1][currentSquarePos.second].second
                        if (currentSquareDistance + 1 < distanceToNextSquare) {
                            visited[currentSquarePos.first + 1][currentSquarePos.second] = Pair(
                                visited[currentSquarePos.first + 1][currentSquarePos.second].first,
                                currentSquareDistance + 1
                            )
                        }
                    }
                }
                if (currentSquarePos.second - 1 >= 0) {
                    if (atMostOneStepHigher(
                            hill,
                            currentSquarePos,
                            Pair(currentSquarePos.first, currentSquarePos.second - 1)
                        )
                    ) {
                        nextSquares.add(Pair(currentSquarePos.first, currentSquarePos.second - 1))
                        val distanceToNextSquare = visited[currentSquarePos.first][currentSquarePos.second - 1].second
                        if (currentSquareDistance + 1 < distanceToNextSquare) {
                            visited[currentSquarePos.first][currentSquarePos.second - 1] = Pair(
                                visited[currentSquarePos.first][currentSquarePos.second - 1].first,
                                currentSquareDistance + 1
                            )
                        }
                    }
                }
                if (currentSquarePos.second + 1 < hill.first().size) {
                    if (atMostOneStepHigher(
                            hill,
                            currentSquarePos,
                            Pair(currentSquarePos.first, currentSquarePos.second + 1)
                        )
                    ) {
                        nextSquares.add(Pair(currentSquarePos.first, currentSquarePos.second + 1))
                        val distanceToNextSquare = visited[currentSquarePos.first][currentSquarePos.second + 1].second
                        if (currentSquareDistance + 1 < distanceToNextSquare) {
                            visited[currentSquarePos.first][currentSquarePos.second + 1] = Pair(
                                visited[currentSquarePos.first][currentSquarePos.second + 1].first,
                                currentSquareDistance + 1
                            )
                        }
                    }
                }
            }
        }

        var endPosition = Pair(0, 0)
        hill
            .indices
            .forEach { row ->
                hill.first().indices
                    .forEach { column -> if (hill[row][column] == 'E') endPosition = Pair(row, column) }
            }

        return visited[endPosition.first][endPosition.second].second
    }

    private fun atMostOneStepHigher(hill: List<CharArray>, current: Pair<Int, Int>, next: Pair<Int, Int>): Boolean {
        return if (hill[current.first][current.second] == 'S') {
            hill[next.first][next.second] == 'a'
        } else if (hill[next.first][next.second] == 'E') {
            true
        } else {
            hill[next.first][next.second].minus(hill[current.first][current.second]) <= 1
        }
    }

}