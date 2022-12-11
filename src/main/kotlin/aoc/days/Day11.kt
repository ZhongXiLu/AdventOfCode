package aoc.days

import aoc.Day
import java.math.BigInteger

private val mod = BigInteger.valueOf(9699690)

class Day11 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val keepAway = initializeKeepAwayGame(input)

        keepAway.playRounds(20)

        return keepAway.monkeys
            .asSequence()
            .map { it.inspectionCount }
            .sortedDescending()
            .chunked(2)
            .map { (largest, secondLargest) -> largest * secondLargest }
            .first()
    }

    override fun solvePart2(input: List<String>): Any {
        val keepAway = initializeKeepAwayGame(input)
        keepAway.worryAboutSomeMonkeysStealingYourShit = false

        keepAway.playRounds(10000)
        println(keepAway.monkeys)

        return keepAway.monkeys
            .asSequence()
            .map { it.inspectionCount }
            .sortedDescending()
            .chunked(2)
            .map { (largest, secondLargest) -> largest * secondLargest }
            .first()
    }
}

private class KeepAway {
    val monkeys = mutableListOf<Monkey>()
    var worryAboutSomeMonkeysStealingYourShit = true

    fun playRounds(rounds: Int) {
        repeat(rounds) {
            monkeys.forEach { monkey ->
                val testResults = monkey.items
                    .onEach { monkey.inspectionCount++ }
                    .map { item -> monkey.operation.invoke(item) }
                    .map { item ->
                        if (worryAboutSomeMonkeysStealingYourShit)
                            item.div(BigInteger.valueOf(3))
                        else
                            item.remainder(mod)
                    }
                    .groupBy { item -> monkey.test.invoke(item) }

                testResults[true]?.forEach { item -> monkeys[monkey.monkeyNrIfTestIsTrue].items.add(item) }
                testResults[false]?.forEach { item -> monkeys[monkey.monkeyNrIfTestIsFalse].items.add(item) }

                monkey.items.clear()
            }
        }
    }

}

private data class Monkey(
    val items: MutableList<BigInteger>,
    val operation: (BigInteger) -> BigInteger,
    val test: (BigInteger) -> Boolean,
    val monkeyNrIfTestIsTrue: Int,
    val monkeyNrIfTestIsFalse: Int,
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
                test.toTest(),
                ifTrue.split(" ").last().toInt(),
                ifFalse.split(" ").last().toInt(),
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

private fun String.toTest(): (BigInteger) -> Boolean {
    return { worryLevel -> worryLevel % this.split(" ").last().toBigInteger() == BigInteger.ZERO }
}

operator fun <T> List<T>.component6() = this[5]
