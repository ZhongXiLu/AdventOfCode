package aoc.years.year2023

import aoc.Day

@Year2023
class Day07 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val hands = input.map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }

        return hands.sortedWith { h1, h2 -> h1.first compareHandTo h2.first }
                .mapIndexed { rank, card -> (rank + 1) * card.second }
                .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        val hands = input.map { it.split(" ") }.map { Pair(it[0], it[1].toInt()) }

        return hands.sortedWith { h1, h2 -> h1.first compareRealHandTo h2.first }
                .mapIndexed { rank, card -> (rank + 1) * card.second }
                .sum()
    }

}

private infix fun String.compareHandTo(otherHand: String): Int {
    val thisHandType = this.getHandType()
    val otherHandType = otherHand.getHandType()

    if (thisHandType != otherHandType) {
        return if (thisHandType < otherHandType) -1 else 1
    }

    this.indices.forEach {
        if (this[it] != otherHand[it]) {
            return this[it] lowerStrengthThan otherHand[it]
        }
    }

    return 0
}

private fun String.getHandType(): Int {
    val cardCount = this.groupingBy { it }.eachCount().toList().sortedBy { it.second }
    if (cardCount.last().second == 5) {
        return 7
    } else if (cardCount.last().second == 4) {
        return 6
    } else if (cardCount.first().second == 2 && cardCount.last().second == 3) {
        return 5
    } else if (cardCount.last().second == 3) {
        return 4
    } else if (cardCount[1].second == 2 && cardCount.last().second == 2) {
        return 3
    } else if (cardCount.last().second == 2) {
        return 2
    } else {
        return 1
    }
}

val cardStrength = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2').reversed()

private infix fun Char.lowerStrengthThan(other: Char): Int {
    return if (cardStrength.indexOf(this) < cardStrength.indexOf(other)) -1 else 1
}

private infix fun String.compareRealHandTo(otherHand: String): Int {
    val thisHandType = this.getRealHandType()
    val otherHandType = otherHand.getRealHandType()

    if (thisHandType != otherHandType) {
        return if (thisHandType < otherHandType) -1 else 1
    }

    this.indices.forEach {
        if (this[it] != otherHand[it]) {
            return this[it] realLowerStrengthThan otherHand[it]
        }
    }

    return 0
}

private fun String.getRealHandType(): Int {
    val cardCount = this.groupingBy { it }.eachCount().toList().sortedBy { it.second }.filter { it.first != 'J' }
    val jokerCount = this.count { it == 'J' }

    if (cardCount.isEmpty()) {
        return 7    // all jokers
    }

    if (cardCount.last().second + jokerCount == 5) {
        return 7
    } else if (cardCount.last().second + jokerCount == 4) {
        return 6
    } else if (cardCount.first().second == 2 + jokerCount && cardCount.last().second == 3) {
        return 5
    } else if (cardCount.first().second == 2 && cardCount.last().second + jokerCount == 3) {
        return 5
    } else if (cardCount.last().second + jokerCount == 3) {
        return 4
    } else if (cardCount[1].second + jokerCount == 2 && cardCount.last().second == 2) {
        return 3
    } else if (cardCount[1].second == 2 && cardCount.last().second + jokerCount == 2) {
        return 3
    } else if (cardCount.last().second + jokerCount == 2) {
        return 2
    } else {
        return 1
    }
}

val realCardStrength = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J').reversed()

private infix fun Char.realLowerStrengthThan(other: Char): Int {
    return if (realCardStrength.indexOf(this) < realCardStrength.indexOf(other)) -1 else 1
}
