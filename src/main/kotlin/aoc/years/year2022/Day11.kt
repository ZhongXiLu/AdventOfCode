package aoc.years.year2022

import aoc.Day
import java.math.BigInteger

private val mod = BigInteger.valueOf(9699690)   // = 13*3*7*2*19*5*11*17

@Year2022
class Day11 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val keepAway = initializeKeepAwayGame(input)

        keepAway.playRounds(20)

        return keepAway.getLevelOfMonkeyBusiness()
    }

    override fun solvePart2(input: List<String>): Any {
        val keepAway = initializeKeepAwayGame(input)
        keepAway.worryAboutSomeMonkeysStealingYourShit = false

        keepAway.playRounds(10000)

        return keepAway.getLevelOfMonkeyBusiness()
    }
}

private class KeepAway {
    val monkeys = mutableListOf<Monkey>()
    var worryAboutSomeMonkeysStealingYourShit = true

    fun playRounds(rounds: Int) {
        repeat(rounds) {
            monkeys.forEach { monkey ->
                monkey.items
                    .onEach { monkey.inspectionCount++ }
                    .map { item -> monkey.operation.invoke(item) }
                    .map { item -> if (worryAboutSomeMonkeysStealingYourShit) item.div(BigInteger.valueOf(3)) else item }
                    .map { item -> item.remainder(mod) }
                    .forEach { item -> monkeys[monkey.test.invoke(item)].items.add(item) }

                monkey.items.clear()
            }
        }
    }

    fun getLevelOfMonkeyBusiness(): BigInteger {
        return monkeys
            .asSequence()
            .map { it.inspectionCount }
            .sortedDescending()
            .chunked(2)
            .map { (largest, secondLargest) -> largest * secondLargest }
            .first()
    }

}

private data class Monkey(
    val items: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val test: (BigInteger) -> Int,
    var inspectionCount: BigInteger = BigInteger.ZERO
)

private fun initializeKeepAwayGame(input: List<String>): KeepAway {
    val keepAway = KeepAway()

    input
        .chunked(7)
        .map { (_, startingItems, operation, test, ifTrue, ifFalse) ->
            Monkey(
                startingItems.substringAfter(": ").split(", ").map { it.toBigInteger() }.toMutableList(),
                operation.toOperation(),
                test.toTest(ifTrue.split(" ").last().toInt(), ifFalse.split(" ").last().toInt())
            )
        }
        .forEach(keepAway.monkeys::add)

    return keepAway
}

private fun String.toOperation(): (BigInteger) -> BigInteger {
    val operand = this.split(" ").last()
    if (this.contains("*")) {
        return { worryLevel -> worryLevel * if (operand != "old") operand.toBigInteger() else worryLevel }
    } else {
        return { worryLevel -> worryLevel + if (operand != "old") operand.toBigInteger() else worryLevel }
    }
}

private fun String.toTest(monkeyNrIfTestIsTrue: Int, monkeyNrIfTestIsFalse: Int): (BigInteger) -> Int {
    return { worryLevel ->
        if (worryLevel % this.split(" ").last().toBigInteger() == BigInteger.ZERO) {
            monkeyNrIfTestIsTrue
        } else {
            monkeyNrIfTestIsFalse
        }
    }
}

private operator fun <T> List<T>.component6() = this[5]
