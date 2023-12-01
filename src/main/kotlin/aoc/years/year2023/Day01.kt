package aoc.years.year2023

import aoc.Day

@Year2023
class Day01 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.sumOf { it.calibrationValue() }
    }

    override fun solvePart2(input: List<String>): Any {
        return input.sumOf { it.realCalibrationValue() }
    }

}

private fun String.calibrationValue(): Int {
    val digits = this.filter { it.isDigit() }.map { it.toString() }
    return (digits.first() + digits.last()).toInt()
}

private fun String.realCalibrationValue(): Int {
    val realDigits = listOf(
            "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
            "1", "2", "3", "4", "5", "6", "7", "8", "9"
    )

    val firstDigitOccurrences = realDigits.associateBy({ it }, { this.indexOf(it) })
            .filter { it.value >= 0 }
            .map { Pair(it.key, it.value) }
            .sortedBy { it.second }
            .map { it.first }

    val lastDigitOccurrences = realDigits.associateBy({ it }, { this.lastIndexOf(it) })
            .filter { it.value >= 0 }
            .map { Pair(it.key, it.value) }
            .sortedBy { it.second }
            .map { it.first }

    return (firstDigitOccurrences.first().toRealDigit() + lastDigitOccurrences.last().toRealDigit()).toInt()
}

private fun String.toRealDigit(): String {
    return when {
        this == "one" -> "1"
        this == "two" -> "2"
        this == "three" -> "3"
        this == "four" -> "4"
        this == "five" -> "5"
        this == "six" -> "6"
        this == "seven" -> "7"
        this == "eight" -> "8"
        this == "nine" -> "9"
        else -> this
    }
}
