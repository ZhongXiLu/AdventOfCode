package aoc.years.year2021

import aoc.Day

@Year2021
class Day01 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .map { it.toInt() }
            .zipWithNext()
            .count { (d1, d2) -> d1 < d2 }
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .map { it.toInt() }
            .windowed(3)
            .zipWithNext()
            .count { (m1, m2) -> m1.sum() < m2.sum() }
    }

}