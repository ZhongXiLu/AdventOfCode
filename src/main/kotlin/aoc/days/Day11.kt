package aoc.days

import aoc.Day

class Day11 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val keepAway = initializeKeepAwayGame(input)

        keepAway.playRounds(20)
        println(keepAway.monkeys)

        return keepAway.monkeys
            .asSequence()
            .map { it.inspectionCount }
            .sortedDescending()
            .chunked(2)
            .map { (largest, secondLargest) -> largest * secondLargest }
            .first()
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private class KeepAway {
    val monkeys = mutableListOf<Monkey>()

    fun playRounds(rounds: Int): Any {
        repeat(rounds) {
            monkeys.forEach { monkey ->
                val testResults = monkey.items
                    .onEach { monkey.inspectionCount++ }
                    .map { item -> monkey.operation.invoke(item) }
                    .map { item -> item.div(3) }
                    .groupBy { item -> monkey.test.invoke(item) }

                testResults[true]?.forEach { item -> monkeys[monkey.monkeyNrIfTestIsTrue].items.add(item) }
                testResults[false]?.forEach { item -> monkeys[monkey.monkeyNrIfTestIsFalse].items.add(item) }

                monkey.items.clear()
            }
        }
        return this
    }

}

private data class Monkey(
    val items: MutableList<Int>,
    val operation: (Int) -> Int,
    val test: (Int) -> Boolean,
    val monkeyNrIfTestIsTrue: Int,
    val monkeyNrIfTestIsFalse: Int,
    var inspectionCount: Int = 0
)

private fun initializeKeepAwayGame(input: List<String>): KeepAway {
    val keepAway = KeepAway()

    input
        .chunked(7)
        .map { (_, startingItems, operation, test, ifTrue, ifFalse) ->
            Monkey(
                startingItems.substringAfter(": ").split(", ").map { it.toInt() }.toMutableList(),
                operation.toOperation(),
                test.toTest(),
                ifTrue.split(" ").last().toInt(),
                ifFalse.split(" ").last().toInt(),
            )
        }
        .forEach(keepAway.monkeys::add)

    return keepAway
}

private fun String.toOperation(): (Int) -> Int {
    val operand = this.split(" ").last()
    if (this.contains("*")) {
        return { worryLevel -> worryLevel * if (operand != "old") operand.toInt() else worryLevel }
    } else {
        return { worryLevel -> worryLevel + if (operand != "old") operand.toInt() else worryLevel }
    }
}

private fun String.toTest(): (Int) -> Boolean {
    return { worryLevel -> worryLevel % this.split(" ").last().toInt() == 0 }
}

operator fun <T> List<T>.component6() = this[5]
