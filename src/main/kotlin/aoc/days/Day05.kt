package aoc.days

import aoc.Day
import java.util.*

private const val NR_OF_STACKS = 9
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

        return stacks.top()
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

        return stacks.top()
    }

    private fun createStacks(input: List<String>): MutableList<ArrayDeque<Char>> {
        val stacks = (1..NR_OF_STACKS)
            .map { ArrayDeque<Char>() }
            .fold(mutableListOf<ArrayDeque<Char>>()) { acc, stack ->
                run {
                    acc.add(stack)
                    return@fold acc
                }
            }

        input
            .asSequence()
            .takeWhile { !it.startsWith(" 1 ") }
            .toList()
            .reversed()
            .map { toCrateLine(it) }
            .forEach { crateLine ->
                crateLine.forEachIndexed { stackNr, crate -> if (crate.isLetter()) stacks[stackNr].push(crate) }
            }

        return stacks
    }

    private fun toCrateLine(rawLine: String): List<Char> {
        return (0 until NR_OF_STACKS)
            .map { rawLine[it * 4 + 1] }
            .toList()
    }

    private fun List<String>.toCargoCommands(): List<Triple<Int, Int, Int>> =
        dropWhile { !it.startsWith("move") }
            .map { CARGO_COMMAND.find(it)!!.destructured }
            .map { (cargoAmount, stackFrom, stackTo) ->
                Triple(cargoAmount.toInt(), stackFrom.toInt() - 1, stackTo.toInt() - 1)
            }

    private fun List<ArrayDeque<Char>>.top(): String =
        map { it.first.toString() }
            .reduce { acc, crate -> acc + crate }
}
