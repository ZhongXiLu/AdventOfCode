package aoc.years.year2024

import aoc.Day

@Year2024
class Day13 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.chunked(4)
            .map { ClawMachine.of(it) }
            .sumOf { it.win() }
    }

    override fun solvePart2(input: List<String>): Any {
        return input.chunked(4)
            .map { ClawMachine.of(it) }
            .map { it.correctPrizes() }
            .sumOf { it.win() }
    }
}

class ClawMachine(private val x1: Double, private val y1: Double, private val x2: Double, private val y2: Double, private val z1: Double, private val z2: Double) {
    companion object {
        fun of(input: List<String>): ClawMachine {
            val buttonLine = Regex(".*: X\\+(\\d+), Y\\+(\\d+)")
            val (ax, bx) = buttonLine.find(input[0])!!.destructured
            val (ay, by) = buttonLine.find(input[1])!!.destructured

            val prizeLine = Regex("Prize: X=(\\d+), Y=(\\d+)")
            val (px, py) = prizeLine.find(input[2])!!.destructured

            return ClawMachine(ax.toDouble(), ay.toDouble(), bx.toDouble(), by.toDouble(), px.toDouble(), py.toDouble())
        }
    }

    fun win(): Long {
        val b = (z2 * x1 - z1 * x2) / (y2 * x1 - y1 * x2)
        val a = (z1 - b * y1) / x1

        if (a >= 0 && b >= 0 && a % 1 == 0.0 && b % 1 == 0.0) {
            return 3 * a.toLong() + b.toLong()
        }

        return 0L
    }

    fun correctPrizes(): ClawMachine {
        return ClawMachine(x1, y1, x2, y2, z1 + 10000000000000L, z2 + 10000000000000L)
    }

}
