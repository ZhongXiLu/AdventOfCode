package aoc.years.year2021

import aoc.Day

@Year2021
class Day08 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.asSequence()
            .map { it.substringAfter("| ") }
            .map { it.split(" ") }
            .flatten()
            .map { it.length }
            .filter { it == 2 || it == 3 || it == 4 || it == 7 }
            .count()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}