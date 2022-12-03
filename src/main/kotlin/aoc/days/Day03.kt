package aoc.days

import aoc.Day

class Day03 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .asSequence()
            .map { it.chunked(it.length / 2) }
            .map { (first, second) -> Pair(first.toCharArray(), second.toCharArray()) }
            .map { (first, second) -> first.intersect(second.toSet())}
            .map { commonItems -> commonItems.sumOf { getPriority(it) } }
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .asSequence()
            .chunked(3)
            .map { (first, second, third) -> Triple(first.toCharArray(), second.toCharArray(), third.toCharArray()) }
            .map { (first, second, third) -> first.intersect(second.toSet()).intersect(third.toSet())}
            .map { commonItems -> commonItems.sumOf { getPriority(it) } }
            .sum()
    }

    private fun getPriority(item: Char): Int {
        return if (item.isLowerCase()) {
            item.code - 96      // Lowercase ASCII values start at 97
        } else {
            item.code - 38      // Uppercase ASCII values start at 65
        }
    }

}