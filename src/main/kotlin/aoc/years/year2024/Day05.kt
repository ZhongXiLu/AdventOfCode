package aoc.years.year2024

import aoc.Day

@Year2024
class Day05 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val rules = getRules(input)

        return getUpdates(input)
            .filter { it.isInOrder(rules) }
            .sumOf { it.middle() }
    }

    override fun solvePart2(input: List<String>): Any {
        val rules = getRules(input)

        return getUpdates(input)
            .filter { !it.isInOrder(rules) }
            .map { it.sortedWith(numberComparator(rules)) }
            .sumOf { it.middle() }
    }

    private fun getRules(input: List<String>) = input.filter { it.contains('|') }
        .map { it.split("|") }
        .fold(mutableMapOf<Int, List<Int>>()) { map, rule ->
            map[rule[0].toInt()] = map.getOrPut(rule[0].toInt()) { mutableListOf() } + rule[1].toInt()
            return@fold map
        }
}

private fun getUpdates(input: List<String>) = input.asSequence()
    .filter { it.contains(',') }
    .map { it.split(',').map(String::toInt) }

private fun numberComparator(rules: MutableMap<Int, List<Int>>): java.util.Comparator<Int> {
    return Comparator { n1, n2 ->
        when {
            rules.getOrDefault(n1, listOf()).contains(n2) -> 1
            rules.getOrDefault(n2, listOf()).contains(n1) -> -1
            else -> 0
        }
    }
}

private fun List<Int>.isInOrder(rules: MutableMap<Int, List<Int>>): Boolean {
    return this.withIndex().all { (index, value) ->
        val followingNumbers = rules.getOrDefault(value, listOf())
        return@all this.hasNumberAfterIndex(index, followingNumbers)
    }
}

private fun List<Int>.hasNumberAfterIndex(index: Int, numbers: List<Int>): Boolean {
    val followingNumberIndex = this.mapIndexed { i, number -> if (numbers.contains(number)) i else -1 }.filter { it != -1 }
    return followingNumberIndex.all { it >= index }
}

private fun List<Int>.middle(): Int {
    return this[size / 2]
}
