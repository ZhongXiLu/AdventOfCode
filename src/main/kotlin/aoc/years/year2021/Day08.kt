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
        return input.map { it.split(" | ") }
            .map { Pair(it[0].split(" "), it[1].split(" ")) }
            .sumOf { calculateOutputNumber(it.first, it.second) }
    }

    private fun calculateOutputNumber(signalPatterns: List<String>, outputDigits: List<String>): Int {
        val digits = buildDigitMap(signalPatterns)

        return outputDigits.map { digits.getDigit(it) }
            .reduce { acc, digit -> acc + digit }
            .toInt()
    }

    private fun buildDigitMap(signalPatterns: List<String>): MutableMap<String, String> {
        val digits = mutableMapOf<String, String>()

        digits["1"] = signalPatterns.first { it.length == 2 }
        digits["7"] = signalPatterns.first { it.length == 3 }
        digits["4"] = signalPatterns.first { it.length == 4 }
        digits["8"] = signalPatterns.first { it.length == 7 }

        digits["9"] = signalPatterns.filter { it.length == 6 }.first { it.toSet().containsAll(digits["4"]!!.toSet()) }
        digits["0"] = signalPatterns.filter { it.length == 6 }.first { !it.toSet().containsAll(digits["4"]!!.toSet()) && it.toSet().containsAll(digits["1"]!!.toSet()) }
        digits["6"] = signalPatterns.filter { it.length == 6 }.first { !it.toSet().containsAll(digits["4"]!!.toSet()) && !it.toSet().containsAll(digits["1"]!!.toSet()) }

        digits["3"] = signalPatterns.filter { it.length == 5 }.first { it.toSet().containsAll(digits["1"]!!.toSet()) }
        digits["5"] = signalPatterns.filter { it.length == 5 }.first { !it.toSet().containsAll(digits["1"]!!.toSet()) && it.toSet().union(digits["6"]!!.toSet()).size == 6 }
        digits["2"] = signalPatterns.filter { it.length == 5 }.first { !it.toSet().containsAll(digits["1"]!!.toSet()) && it.toSet().union(digits["6"]!!.toSet()).size == 7 }

        return digits
    }

}

private fun MutableMap<String, String>.getDigit(pattern: String): String {
    return this.entries.filter { it.value.length == pattern.length }
        .first { it.value.toSet().containsAll(pattern.toSet()) }
        .key
}
