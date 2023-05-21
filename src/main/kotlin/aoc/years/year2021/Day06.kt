package aoc.years.year2021

import aoc.Day

@Year2021
class Day06 : Day() {

    override fun solvePart1(input: List<String>): Any {
        var lanternFish = input.toLanternFish()

        repeat(80) {
            lanternFish = lanternFish
                .map { it.cycle() }
                .flatten()
        }

        return lanternFish.size
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

    private fun List<String>.toLanternFish(): List<Int> = this.first().split(",").map { it.toInt() }

    private fun Int.cycle(): List<Int> {
        return if (this == 0) listOf(6, 8) else listOf(this - 1)
    }
}
