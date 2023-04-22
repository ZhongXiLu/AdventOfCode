package aoc.years.year2021

import aoc.Day

@Year2021
class Day02 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .map { it.toCommand() }
            .fold(Position(0, 0)) { pos, (action, value) ->
                return@fold when (action) {
                    "forward" -> pos.forward(value)
                    "down" -> pos.increaseDepth(value)
                    "up" -> pos.increaseDepth(-value)
                    else -> pos
                }
            }
            .run { this.horizontal * this.depth }
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .map { it.toCommand() }
            .fold(Position(0, 0)) { pos, (action, value) ->
                return@fold when (action) {
                    "forward" -> pos.forward(value)
                    "down" -> pos.increaseAim(value)
                    "up" -> pos.increaseAim(-value)
                    else -> pos
                }
            }
            .run { this.horizontal * this.depth }
    }
}

data class Position(val horizontal: Int, val depth: Int, val aim: Int = 0) {
    fun forward(value: Int): Position = Position(this.horizontal + value, this.depth + this.aim * value, this.aim)
    fun increaseDepth(value: Int): Position = Position(this.horizontal, this.depth + value, this.aim)
    fun increaseAim(value: Int): Position = Position(this.horizontal, this.depth, this.aim + value)
}

private fun String.toCommand(): Pair<String, Int> {
    val (action, value) = this.split(" ")
    return Pair(action, value.toInt())
}
