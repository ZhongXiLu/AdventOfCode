package aoc.years.year2022

import aoc.Day

@Year2022
class Day04 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .asSequence()
            .mapToRanges()
            .map { (first, second) -> rangesOverlap(first, second) }
            .count { it }
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .asSequence()
            .mapToRanges()
            .map { (first, second) -> first.intersect(second).isNotEmpty() }
            .count { it }
    }

    private fun rangesOverlap(firstRange: IntRange, secondRange: IntRange): Boolean {
        return (firstRange.contains(secondRange.first) && firstRange.contains(secondRange.last)) ||
                (secondRange.contains(firstRange.first) && secondRange.contains(firstRange.last))
    }

    private fun Sequence<String>.mapToRanges(): Sequence<Pair<IntRange, IntRange>> =
        map { it.split(",") }
            .map { (first, second) -> Pair(first.split("-"), second.split("-")) }
            .map { (first, second) -> Pair(first[0].toInt()..first[1].toInt(), second[0].toInt()..second[1].toInt()) }

}
