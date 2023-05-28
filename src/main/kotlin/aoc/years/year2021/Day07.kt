package aoc.years.year2021

import aoc.Day
import kotlin.math.abs

@Year2021
class Day07 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val (crabs, min, max) = crabs(input)

        return (min..max).minOf { crabs.calculateFuel(it) }
    }

    override fun solvePart2(input: List<String>): Any {
        val (crabs, min, max) = crabs(input)

        return (min..max).minOf { crabs.calculateFuelWithAdditionalCost(it)}
    }

    private fun crabs(input: List<String>): Triple<Map<Int, Int>, Int, Int> {
        val crabs = input.first()
            .split(",")
            .map { it.toInt() }
            .groupBy { it }
            .mapValues { it.value.size }

        val min = crabs.keys.min()
        val max = crabs.keys.max()

        return Triple(crabs, min, max)
    }

}

private fun Map<Int, Int>.calculateFuel(position: Int): Int {
    return this.entries.sumOf { abs(position - it.key) * it.value }
}

private fun Map<Int, Int>.calculateFuelWithAdditionalCost(position: Int): Int {
    return this.entries.sumOf {
        val steps = abs(position - it.key)
        val cost = (steps * (steps + 1)) / 2
        return@sumOf cost * it.value
    }
}
