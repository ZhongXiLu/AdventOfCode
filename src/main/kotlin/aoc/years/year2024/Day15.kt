package aoc.years.year2024

import aoc.Day

@Year2024
class Day15 : Day() {

    override fun solvePart1(input: List<String>): Any {
        // 8,10,50
        return Warehouse.of(input.take(50))
            .move(getMoves(input.subList(50, input.size)))
            .getSumOfGpsCoordinates()
    }

    override fun solvePart2(input: List<String>): Any {

        return 0
    }

    private fun getMoves(input: List<String>) = input.map { it.split("") }.flatten().filter(String::isNotBlank)

}

class Warehouse(private val warehouse: MutableList<MutableList<String>>) {
    companion object {
        fun of(input: List<String>): Warehouse {
            val warehouse = input.take(50).map { it.split("").filter(String::isNotBlank).toMutableList() }.toMutableList()
            return Warehouse(warehouse)
        }
    }

    fun move(moves: List<String>): Warehouse {
        moves.forEach { move ->
            println(warehouse.joinToString("\n") { it.joinToString("") })

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

            } else {
                warehouse[x][y] = "."
                warehouse[newX][newY] = "@"
            }
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

    fun getSumOfGpsCoordinates(): Int {
        return warehouse.withIndex().sumOf { (x, row) ->
            row.withIndex().sumOf { (y, cell) ->
                if (cell == "O") 100 * x + y else 0
            }
        }
    }
}
