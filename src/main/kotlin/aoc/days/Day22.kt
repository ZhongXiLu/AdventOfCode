package aoc.days

import aoc.Day

class Day22 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val map = Map.of(input.take(input.size - 2))
        val instructions = "(\\d+)|([LR])".toRegex().findAll(input.last()).map(MatchResult::value).toList()

        map.run(instructions)

        return map.getPassword()
    }

    override fun solvePart2(input: List<String>): Any {
        // Hard coded cube layout for part 2 ;)
        // .12
        // .3.
        // 54.
        // 6..

        val map = Map.of(input.take(input.size - 2), cubeLayout = true)
        val instructions = "(\\d+)|([LR])".toRegex().findAll(input.last()).map(MatchResult::value).toList()

        map.run(instructions)

        return map.getPassword()
    }

}

private data class Map(val map: List<CharArray>, val cubeLayout: Boolean = false) {
    var position = getStartingPosition()
    var direction = ">"

    companion object {
        val DIRECTIONS = listOf(">", "v", "<", "^")
        val DIRECTION_MOVEMENT = mapOf(">" to Pair(0, 1), "v" to Pair(1, 0), "<" to Pair(0, -1), "^" to Pair(-1, 0))
        val CUBE_LAYOUT = mapOf(
            1 to mapOf(
                "TOP" to { x: Int -> Pair(Pair(150 + x, 0), ">") }, // 6
                "LEFT" to { x: Int -> Pair(Pair(100 + x, 0), ">") }, // 5
            ),
            2 to mapOf(
                "TOP" to { x: Int -> Pair(Pair(199, 0 + x), "^") }, // 6
                "RIGHT" to { x: Int -> Pair(Pair(100 + x, 99), "<") }, // 4
                "BOTTOM" to { x: Int -> Pair(Pair(50 + x, 99), "<") }, // 3
            ),
            3 to mapOf(
                "RIGHT" to { x: Int -> Pair(Pair(49, 100 + x), "^") }, // 2
                "LEFT" to { x: Int -> Pair(Pair(100, 0 + x), "v") }, // 5
            ),
            4 to mapOf(
                "RIGHT" to { x: Int -> Pair(Pair(0 + x, 149), "<") }, // 2
                "BOTTOM" to { x: Int -> Pair(Pair(150 + x, 49), "<") }, // 6
            ),
            5 to mapOf(
                "TOP" to { x: Int -> Pair(Pair(50 + x, 50), ">") }, // 3
                "LEFT" to { x: Int -> Pair(Pair(0 + x, 50), ">") }, // 1
            ),
            6 to mapOf(
                "RIGHT" to { x: Int -> Pair(Pair(149, 50 + x), "^") }, // 4
                "BOTTOM" to { x: Int -> Pair(Pair(0, 100 + x), "v") }, // 2
                "LEFT" to { x: Int -> Pair(Pair(0, 50 + x), "v") }, // 1
            ),
        )

        fun of(input: List<String>, cubeLayout: Boolean = false): Map {
            return Map(input.map { it.toCharArray() }, cubeLayout)
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
            var newDirection = direction
            val currentCube = getCurrentCube(position)

            when {
                newPosition.first < 0 || (direction == "^" && map[newPosition.first][newPosition.second].isWhitespace()) -> {
                    if (cubeLayout) {
                        val (newPositionOnSide, newDirectionOnSide) = currentCube.getAdjacentSide("TOP", newPosition.second)
                        newPosition = newPositionOnSide
                        newDirection = newDirectionOnSide
                    } else {
                        newPosition = getBottomMostPosition(newPosition.second)
                    }
                }

                newPosition.first >= map.size
                        || (direction == "v" && newPosition.second !in map[newPosition.first].indices)
                        || (direction == "v" && map[newPosition.first][newPosition.second].isWhitespace()) -> {
                    if (cubeLayout) {
                        val (newPositionOnSide, newDirectionOnSide) = currentCube.getAdjacentSide("BOTTOM", newPosition.second)
                        newPosition = newPositionOnSide
                        newDirection = newDirectionOnSide
                    } else {
                        newPosition = getTopMostPosition(newPosition.second)
                    }
                }

                newPosition.second < 0 || (direction == "<" && map[newPosition.first][newPosition.second].isWhitespace()) -> {
                    if (cubeLayout) {
                        val (newPositionOnSide, newDirectionOnSide) = currentCube.getAdjacentSide("LEFT", newPosition.first)
                        newPosition = newPositionOnSide
                        newDirection = newDirectionOnSide
                    } else {
                        newPosition = getRightMostPosition(newPosition.first)
                    }
                }

                direction == ">" && newPosition.second !in map[newPosition.first].indices -> {
                    if (cubeLayout) {
                        val (newPositionOnSide, newDirectionOnSide) = currentCube.getAdjacentSide("RIGHT", newPosition.first)
                        newPosition = newPositionOnSide
                        newDirection = newDirectionOnSide
                    } else {
                        newPosition = getLeftMostPosition(newPosition.first)
                    }
                }
            }

            if (map[newPosition.first][newPosition.second] == '.'
                || map[newPosition.first][newPosition.second] == '<'
                || map[newPosition.first][newPosition.second] == '>'
                || map[newPosition.first][newPosition.second] == 'v'
                || map[newPosition.first][newPosition.second] == '^') {
                position = newPosition
                direction = newDirection
                map[newPosition.first][newPosition.second] = newDirection.single()
            }
        }
    }

    private fun getCurrentCube(position: Pair<Int, Int>): Int {
        return when {
            position.first in 0 until 50 && position.second in 50 until 100 -> 1
            position.first in 0 until 50 && position.second in 100 until 150 -> 2
            position.first in 50 until 100 && position.second in 50 until 100 -> 3
            position.first in 100 until 150 && position.second in 50 until 100 -> 4
            position.first in 100 until 150 && position.second in 0 until 50 -> 5
            position.first in 150 until 200 && position.second in 0 until 50 -> 6
            else -> throw IllegalArgumentException("Unknown position on cube: $position")
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

    private fun Int.getAdjacentSide(side: String, currentRowOrColumn: Int): Pair<Pair<Int, Int>, String> {
        val offset = currentRowOrColumn % 50
        return CUBE_LAYOUT[this]!![side]!!.invoke(offset)
    }

    fun getPassword(): Int {
        return 1000 * (position.first + 1) + 4 * (position.second + 1) + DIRECTIONS.indexOf(direction)
    }

    override fun toString(): String {
        return map.mapIndexed { row, chars ->
            chars.mapIndexed { column, c ->
                if (position.first == row && position.second == column) return@mapIndexed direction else c
            }.joinToString("")
        }.joinToString("\n")
    }

}
