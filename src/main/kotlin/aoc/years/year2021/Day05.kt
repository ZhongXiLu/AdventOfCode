package aoc.years.year2021

import aoc.Day

@Year2021
class Day05 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val ocean = Array(1000) { Array(1000) { 0 } }

        input.map { Vent.of(it) }
            .map { it.getCoords() }
            .flatten()
            .forEach { (x, y) -> ocean[x][y]++ }

        return ocean.flatten()
            .count { it >= 2 }
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
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

    fun getCoords(): List<Pair<Int, Int>> {
        return if (fromX == toX) {
            (fromY..toY)
                .map { Pair(fromX, it) }
                .plus((toY..fromY)
                    .map { Pair(fromX, it) })
        } else if (fromY == toY) {
            (fromX..toX)
                .map { Pair(it, fromY) }
                .plus((toX..fromX)
                    .map { Pair(it, fromY) })
        } else {
            listOf()
        }
    }
}
