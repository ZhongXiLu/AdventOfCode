package aoc.years.year2021

import aoc.Day

@Year2021
class Day04 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val (bingoCards, bingoNumbers) = bingo(input)

        return bingoNumbers
            .forEach { number ->
                bingoCards.forEach { it.mark(number) }
                val winningCards = bingoCards.getWinningCards()
                if (winningCards.isNotEmpty()) return winningCards.first().calculateScore(number)
            }
    }

    override fun solvePart2(input: List<String>): Any {
        val (bingoCards, bingoNumbers) = bingo(input)

        val winningBingoCards = mutableListOf<BingoCard>()
        val bingoScores = mutableListOf<Int>()

        bingoNumbers.forEach { number ->
            bingoCards.forEach { it.mark(number) }
            bingoCards.getWinningCards().filter { !winningBingoCards.contains(it) }.forEach {
                bingoScores.add(it.calculateScore(number))
                winningBingoCards.add(it)
            }
        }

        return bingoScores.last()
    }

    private fun bingo(input: List<String>): Pair<List<BingoCard>, List<String>> {
        val bingoCards = input.drop(1)
            .filter { it.isNotBlank() }
            .chunked(5)
            .map { BingoCard.of(it) }

        val bingoNumbers = input.first()
            .split(",")

        return Pair(bingoCards, bingoNumbers)
    }

}

private fun List<BingoCard>.getWinningCards(): List<BingoCard> {
    return this.filter { it.isWinning() }
}

data class BingoCard(var card: List<List<String>>, val input: List<String>) {
    companion object {
        fun of(input: List<String>): BingoCard {
            return BingoCard(input.map { it.split(" ").filter(String::isNotBlank) }, input)
        }
    }

    fun mark(calledNumber: String) {
        card = card.map { row ->
            row.map { number ->
                if (number == calledNumber) "X" else number
            }
        }
    }

    fun isWinning(): Boolean = hasWinningRow() || hasWinningColumn()

    private fun hasWinningRow(): Boolean {
        return card.any { isWinningRow(it) }
    }

    private fun isWinningRow(row: List<String>): Boolean {
        return row.all { number -> number == "X" }
    }

    private fun hasWinningColumn(): Boolean {
        return card.first().indices.any { isWinningColumn(it) }
    }

    private fun isWinningColumn(column: Int): Boolean {
        return card.all { it[column] == "X" }
    }

    fun calculateScore(lastCalledNumber: String): Int {
        return card.flatten()
            .filter { it != "X" }
            .sumOf { it.toInt() }
            .times(lastCalledNumber.toInt())
    }

    override fun hashCode(): Int {
        return input.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BingoCard
        return input == other.input
    }

}

