package aoc.days

import aoc.Day
import java.util.*

private val CARGO_COMMAND = "move (\\d+) from (\\d+) to (\\d+)".toRegex()

class Day05 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val stacks = createStacks(input)

        input
            .toCargoCommands()
            .forEach { (cargoAmount, stackFrom, stackTo) ->
                run {
                    (1..cargoAmount)
                        .map { stacks[stackFrom].pop() }
                        .forEach { stacks[stackTo].push(it) }
                }
            }

        return stacks
            .map { it.first.toString() }
            .reduce { acc, crate -> acc + crate }
    }

    override fun solvePart2(input: List<String>): Any {
        val stacks = createStacks(input)

        input
            .toCargoCommands()
            .forEach { (cargoAmount, stackFrom, stackTo) ->
                run {
                    (1..cargoAmount)
                        .map { stacks[stackFrom].pop() }
                        .reversed()
                        .forEach { stacks[stackTo].push(it) }
                }
            }

        return stacks
            .map { it.first.toString() }
            .reduce { acc, crate -> acc + crate }
    }

    private fun createStacks(input: List<String>): MutableList<ArrayDeque<Char>> {
        val stacks = mutableListOf<ArrayDeque<Char>>()
        for (i in 1..9) {
            stacks.add(ArrayDeque())
        }

        input
            .asSequence()
            .takeWhile { !it.startsWith(" 1 ") }
            .toList()
            .reversed()
            .map { toCrateLine(it) }
            .forEach { crates ->
                crates.forEachIndexed { stackNr, crate -> if (crate.isLetter()) stacks[stackNr].push(crate) }
            }

        return stacks
    }

    private fun toCrateLine(rawLine: String): List<Char> {
        return (0..8)
            .map { rawLine[it * 4 + 1] }
            .toList()
    }

    private fun List<String>.toCargoCommands(): List<Triple<Int, Int, Int>> =
        dropWhile { !it.startsWith("move") }
            .map { CARGO_COMMAND.find(it)!!.destructured }
            .map { (cargoAmount, stackFrom, stackTo) ->
                Triple(cargoAmount.toInt(), stackFrom.toInt() - 1, stackTo.toInt() - 1)
            }

}
