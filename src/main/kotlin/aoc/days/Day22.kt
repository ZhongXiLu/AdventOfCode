package aoc.days

import aoc.Day

class Day22 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val map = Map.of(input.take(input.size - 2))
        val instructions = "(\\d+)|([LR])".toRegex().findAll(input.last()).map(MatchResult::value).toList()

        map.run(instructions)

        return 1000 * (map.position.first + 1) + 4 * (map.position.second + 1) + Map.DIRECTIONS.indexOf(map.direction)
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private data class Map(val map: List<CharArray>) {
    var position = getStartingPosition()
    var direction = ">"

    companion object {
        val DIRECTIONS = listOf(">", "v", "<", "^")
        val DIRECTION_MOVEMENT = mapOf(">" to Pair(0, 1), "v" to Pair(1, 0), "<" to Pair(0, -1), "^" to Pair(-1, 0))

        fun of(input: List<String>): Map {
            return Map(input.map { it.toCharArray() })
        }
    }

    fun run(instructions: List<String>) {
        instructions.forEach {
            when {
                "(\\d+)".toRegex().matches(it) -> move(it.toInt())
                "([LR])".toRegex().matches(it) -> rotate(it)
            }
        }
    }

    private fun move(steps: Int) {
        repeat(steps) {
            val movement = DIRECTION_MOVEMENT[direction]!!
            var newPosition = Pair(position.first + movement.first, position.second + movement.second)

            when {
                newPosition.first < 0 || (direction == "^" && map[newPosition.first][newPosition.second].isWhitespace()) -> {
                    newPosition = getBottomMostPosition(newPosition.second)
                }

                newPosition.first >= map.size
                        || (direction == "v" && newPosition.second !in map[newPosition.first].indices)
                        || (direction == "v" && map[newPosition.first][newPosition.second].isWhitespace()) -> {
                    newPosition = getTopMostPosition(newPosition.second)
                }

                newPosition.second < 0 || (direction == "<" && map[newPosition.first][newPosition.second].isWhitespace()) -> {
                    newPosition = getRightMostPosition(newPosition.first)
                }

                direction == ">" && newPosition.second !in map[newPosition.first].indices -> {
                    newPosition = getLeftMostPosition(newPosition.first)
                }
            }

            if (map[newPosition.first][newPosition.second] == '.') {
                position = newPosition
            }
        }
    }

    private fun getTopMostPosition(column: Int): Pair<Int, Int> {
        val topMostRow = map
            .mapIndexed { index, chars -> Pair(index, chars) }
            .filter { (_, chars) -> column in chars.indices }
            .first { (_, chars) -> !chars[column].isWhitespace() }
        return Pair(topMostRow.first, column)
    }

    private fun getBottomMostPosition(column: Int): Pair<Int, Int> {
        val bottomMostRow = map
            .mapIndexed { index, chars -> Pair(index, chars) }
            .filter { (_, chars) -> column in chars.indices }
            .last { (_, chars) -> !chars[column].isWhitespace() }
        return Pair(bottomMostRow.first, column)
    }

    private fun getLeftMostPosition(row: Int): Pair<Int, Int> {
        val leftMostColumn = map[row].indexOfFirst { !it.isWhitespace() }
        return Pair(row, leftMostColumn)
    }

    private fun getRightMostPosition(row: Int): Pair<Int, Int> {
        val leftMostColumn = map[row].indexOfLast { !it.isWhitespace() }
        return Pair(row, leftMostColumn)
    }

    private fun rotate(newDirection: String) {
        val indexOfNextDirection = DIRECTIONS.indexOf(direction).plus(if (newDirection == "R") 1 else -1)
        direction = if (indexOfNextDirection >= DIRECTIONS.size) DIRECTIONS.first() else
            (if (indexOfNextDirection < 0) DIRECTIONS.last() else DIRECTIONS[indexOfNextDirection])
    }

    private fun getStartingPosition(): Pair<Int, Int> {
        return Pair(0, map.first().indexOfFirst { it == '.' })
    }
}
