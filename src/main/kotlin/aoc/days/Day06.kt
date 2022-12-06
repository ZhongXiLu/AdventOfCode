package aoc.days

import aoc.Day

class Day06 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .first()
            .findIndexOfFirstDistinctGroup(4)
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .first()
            .findIndexOfFirstDistinctGroup(14)
    }

    private fun String.findIndexOfFirstDistinctGroup(groupSize: Int): Int =
        toCharArray()
            .asSequence()
            .windowed(groupSize, 1)
            .withIndex()
            .filter { it.value.toSet().size == it.value.size }
            .first()
            .index + groupSize

}
