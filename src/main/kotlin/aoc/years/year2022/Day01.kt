package aoc.years.year2022

import aoc.Day

@Year2022
class Day01 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input

            // Create list for each elf (e.g. [[1, 2], [3, 4, 5] ])
            .joinToString()
            .splitToSequence(", ,")
            .map { it.split(",") }
            .map { it.map { it2 -> it2.trim().toInt() } }

            .map { it.sum() }
            .max()
    }

    override fun solvePart2(input: List<String>): Any {
        return input

            // Create list for each elf (e.g. [[1, 2], [3, 4, 5] ])
            .joinToString()
            .splitToSequence(", ,")
            .map { it.split(",") }
            .map { it.map { it2 -> it2.trim().toInt() } }

            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()
    }

}