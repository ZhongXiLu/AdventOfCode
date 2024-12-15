package aoc.years.year2024

import aoc.Day

@Year2024
class Day15 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Warehouse.of(input.take(50))
            .move(getMoves(input.subList(50, input.size)))
            .getSumOfGpsCoordinates()
    }

    override fun solvePart2(input: List<String>): Any {
        return Warehouse.ofWithExpansion(input.take(50))
            .move(getMoves(input.subList(50, input.size)))
            .getSumOfGpsCoordinates()
    }

    private fun getMoves(input: List<String>) = input.map { it.split("") }.flatten().filter(String::isNotBlank)

}

class Warehouse(private val warehouse: MutableList<MutableList<String>>) {
    companion object {
        fun of(input: List<String>): Warehouse {
            val warehouse =
                input.take(50).map { it.split("").filter(String::isNotBlank).toMutableList() }.toMutableList()
            return Warehouse(warehouse)
        }

        fun ofWithExpansion(input: List<String>): Warehouse {
            val warehouse = input.take(50).map {
                it.split("").filter(String::isNotBlank).map { char ->
                    when (char) {
                        "#" -> listOf("#", "#")
                        "O" -> listOf("[", "]")
                        "." -> listOf(".", ".")
                        "@" -> listOf("@", ".")
                        else -> listOf()
                    }
                }.flatten().toMutableList()
            }.toMutableList()
            return Warehouse(warehouse)
        }
    }

    fun move(moves: List<String>): Warehouse {
        moves.forEach { move ->
            val (x, y) = getRobotPosition()
            val (newX, newY) = getMove(move, x, y)

            if (newX !in warehouse.indices || newY !in warehouse[0].indices || warehouse[newX][newY] == "#") {
                return@forEach
            }

            if (warehouse[newX][newY] == "O") {
                if (moveBox(move, newX, newY)) {
                    warehouse[x][y] = "."
                    warehouse[newX][newY] = "@"
                }

            } else if (warehouse[newX][newY] == "[" || warehouse[newX][newY] == "]") {
                if (moveExpandedBox(move, newX, newY)) {
                    warehouse[x][y] = "."
                    warehouse[newX][newY] = "@"
                }

            } else {
                warehouse[x][y] = "."
                warehouse[newX][newY] = "@"
            }

//            println(move)
//            println(warehouse.joinToString("\n") { it.joinToString("") } + "\n")
        }
        return this
    }

    private fun getRobotPosition(): Pair<Int, Int> {
        warehouse.forEachIndexed { x, row ->
            row.forEachIndexed { y, cell ->
                if (cell == "@") {
                    return Pair(x, y)
                }
            }
        }
        return Pair(-1, -1)
    }

    private fun getMove(move: String, x: Int, y: Int): Pair<Int, Int> {
        return when (move) {
            "^" -> Pair(x - 1, y)
            "v" -> Pair(x + 1, y)
            "<" -> Pair(x, y - 1)
            ">" -> Pair(x, y + 1)
            else -> Pair(x, y)
        }
    }

    private fun moveBox(move: String, x: Int, y: Int): Boolean {
        if (move == ">") {
            (y..<warehouse[x].size).forEach { newY ->
                if (warehouse[x][newY] == "#") {
                    return false
                }
                if (warehouse[x][newY] == ".") {
                    warehouse[x][y] = "."
                    warehouse[x][newY] = "O"
                    return true
                }
            }
        } else if (move == "<") {
            (y downTo 0).forEach { newY ->
                if (warehouse[x][newY] == "#") {
                    return false
                }
                if (warehouse[x][newY] == ".") {
                    warehouse[x][y] = "."
                    warehouse[x][newY] = "O"
                    return true
                }
            }
        } else if (move == "^") {
            (x downTo 0).forEach { newX ->
                if (warehouse[newX][y] == "#") {
                    return false
                }
                if (warehouse[newX][y] == ".") {
                    warehouse[x][y] = "."
                    warehouse[newX][y] = "O"
                    return true
                }
            }
        } else if (move == "v") {
            (x..<warehouse.size).forEach { newX ->
                if (warehouse[newX][y] == "#") {
                    return false
                }
                if (warehouse[newX][y] == ".") {
                    warehouse[x][y] = "."
                    warehouse[newX][y] = "O"
                    return true
                }
            }
        }
        return false
    }

    private fun moveExpandedBox(move: String, x: Int, y: Int): Boolean {
        val connectedBoxes = getConnectedBoxes(move, x, y)

        if (connectedBoxes.isNotEmpty()) {
            if (canMoveBoxes(move, connectedBoxes)) {
                moveBoxes(move, connectedBoxes)
                return true
            }
        }
        return false
    }

    private fun getConnectedBoxes(move: String, x: Int, y: Int): List<Pair<Int, Int>> {
        if (warehouse[x][y] == "]" || warehouse[x][y] == "[") {
            val box = listOf(Pair(x, y), Pair(x, y.plus(if (warehouse[x][y] == "]") -1 else 1)))
            val boxesToCheck = when (move) {
                ">" -> listOf(Pair(x, y + 1))
                "<" -> listOf(Pair(x, y - 1))
                else -> box
            }

            val connectedBoxes = boxesToCheck.map {
                val newMove = getMove(move, it.first, it.second)
                return@map getConnectedBoxes(move, newMove.first, newMove.second)
            }.flatten()
            return box.plus(connectedBoxes).distinct()
        }

        return listOf()
    }

    private fun canMoveBoxes(move: String, boxes: List<Pair<Int, Int>>): Boolean {
        return boxes.all { box ->
            val newMove = getMove(move, box.first, box.second)
            return@all warehouse[newMove.first][newMove.second] != "#"
        }
    }

    private fun moveBoxes(move: String, boxes: List<Pair<Int, Int>>) {
        boxes.map { box ->
            val newMove = getMove(move, box.first, box.second)
            val boxSymbol = warehouse[box.first][box.second]
            warehouse[box.first][box.second] = "."
            return@map Pair(newMove, boxSymbol)
        }.forEach { (newMove, boxSymbol) ->
            warehouse[newMove.first][newMove.second] = boxSymbol
        }
    }

    fun getSumOfGpsCoordinates(): Int {
        return warehouse.withIndex().sumOf { (x, row) ->
            row.withIndex().sumOf { (y, cell) ->
                when (cell) {
                    "O", "[" -> 100 * x + y
                    else -> 0
                }
            }
        }
    }
}
