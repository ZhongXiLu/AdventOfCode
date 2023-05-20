package aoc.years.year2021

import aoc.Day
import kotlin.math.abs

@Year2021
class Day05 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val ocean = Array(1000) { Array(1000) { 0 } }

        input.map { Vent.of(it) }
            .map { it.getHorizontalAndVerticalLineCoords() }
            .flatten()
            .forEach { (x, y) -> ocean[x][y]++ }

        return ocean.flatten()
            .count { it >= 2 }
    }

    override fun solvePart2(input: List<String>): Any {
        val ocean = Array(1000) { Array(1000) { 0 } }

        input.map { Vent.of(it) }
            .map { it.getAllLineCoords() }
            .flatten()
            .forEach { (x, y) -> ocean[x][y]++ }

        return ocean.flatten()
            .count { it >= 2 }
    }

}

data class Vent(val fromX: Int, val fromY: Int, val toX: Int, val toY: Int) {
    companion object {
        private val VENT_INPUT_LINE = "(\\d+),(\\d+) -> (\\d+),(\\d+)".toRegex()
        fun of(input: String): Vent {
            val (fromX, fromY, toX, toY) = VENT_INPUT_LINE.find(input)!!.destructured
            return Vent(fromX.toInt(), fromY.toInt(), toX.toInt(), toY.toInt())
        }

    }

    fun getHorizontalAndVerticalLineCoords(): List<Pair<Int, Int>> {
        return if (fromX == toX) {
            horizontalLine()
        } else if (fromY == toY) {
            verticalLine()
        } else {
            listOf()
        }
    }

    fun getAllLineCoords(): List<Pair<Int, Int>> {
        return if (fromX == toX) {
            horizontalLine()
        } else if (fromY == toY) {
            verticalLine()
        } else {
            diagonalLine()
        }
    }

    private fun horizontalLine(): List<Pair<Int, Int>> {
        val range = if (fromY < toY) (fromY..toY) else (toY..fromY)
        return range.map { Pair(fromX, it) }
    }

    private fun verticalLine(): List<Pair<Int, Int>> {
        val range = if (fromX < toX) (fromX..toX) else (toX..fromX)
        return range.map { Pair(it, fromY) }
    }

    private fun diagonalLine(): List<Pair<Int, Int>> {
        val xIncrement = if (fromX < toX) 1 else -1
        val yIncrement = if (fromY < toY) 1 else -1

        return (0..(abs(fromX - toX)))
            .map { Pair(fromX + it * xIncrement, fromY + it * yIncrement) }
    }
}
