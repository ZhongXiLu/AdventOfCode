package aoc.years.year2024

import aoc.Day

@Year2024
class Day02 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return getReports(input).count { it.safe() }
    }

    override fun solvePart2(input: List<String>): Any {
        return getReports(input).count { it.safeWithToleration() }
    }

    private fun getReports(input: List<String>) = input.map { it.split(" ") }.map { it.map(String::toInt) }
}

private fun List<Int>.safe(): Boolean {
    return this.allIncreasing() || this.allDecreasing()
}

private fun List<Int>.safeWithToleration(): Boolean {
    return this.allIncreasing() || this.allDecreasing() || this.allIncreasingWithToleration() || this.allDecreasingWithToleration()
}

private fun List<Int>.allIncreasing(): Boolean {
    return this.zipWithNext().all { (first, second) -> first in (second + 1)..(second + 3) }
}

private fun List<Int>.allDecreasing(): Boolean {
    return this.zipWithNext().all { (first, second) -> first in (second - 3)..(second - 1) }
}

private fun List<Int>.allIncreasingWithToleration(): Boolean {
    return this.indices.any { i ->
        this.toMutableList().apply { removeAt(i) }.allIncreasing()
    }
}

private fun List<Int>.allDecreasingWithToleration(): Boolean {
    return this.indices.any { i ->
        this.toMutableList().apply { removeAt(i) }.allDecreasing()
    }
}
