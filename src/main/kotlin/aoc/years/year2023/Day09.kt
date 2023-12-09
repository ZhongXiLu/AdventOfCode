package aoc.years.year2023

import aoc.Day

@Year2023
class Day09 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.map { it.split(" ").map(String::toInt) }
            .sumOf { it.getNextValue() }
    }

    override fun solvePart2(input: List<String>): Any {
        return input.map { it.split(" ").map(String::toInt) }
            .sumOf { it.getPreviousValue() }
    }

}

private fun List<Int>.getNextValue(): Int {
    val diffs = this.windowed(2).map { it[1] - it[0] }
    return if (diffs.all { it == 0 }) this.last() else this.last() + diffs.getNextValue()
}

private fun List<Int>.getPreviousValue(): Int {
    val diffs = this.windowed(2).map { it[1] - it[0] }
    return if (diffs.all { it == 0 }) this.first() else this.first() - diffs.getPreviousValue()
}
