package aoc.years.year2023

import aoc.Day
import kotlin.math.pow

@Year2023
class Day04 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.winningNumbers()
                .filter { it.isNotEmpty() }
                .sumOf { 2.0.pow(it.size.toDouble() - 1) }
                .toInt()
    }

    override fun solvePart2(input: List<String>): Any {
        val nrOfWinningNumbers = input.winningNumbers().map { it.size }
        return nrOfWinningNumbers.indices
                .sumOf { getNrOfWinningScratchcards(it, nrOfWinningNumbers) }
    }

    private fun List<String>.winningNumbers(): List<Set<String>> {
        return this.map { it.substringAfter(":").split("|") }
                .map {
                    val winningNumbers = it.component1().split(" ").filter(String::isNotBlank)
                    val drawnNumbers = it.component2().split(" ").filter(String::isNotBlank)
                    return@map winningNumbers.intersect<String>(drawnNumbers.toSet<String>())
                }
    }

    private fun getNrOfWinningScratchcards(scratchCardNumber: Int, winningNumbers: List<Int>): Int {
        val otherWinningCards = (1..winningNumbers[scratchCardNumber])
                .sumOf { getNrOfWinningScratchcards(scratchCardNumber + it, winningNumbers) }
        return 1 + otherWinningCards
    }
}
