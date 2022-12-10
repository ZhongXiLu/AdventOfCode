package aoc.days

import aoc.Day

class Day10 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val cpu = CPU()

        input
            .map { cpu.parseInstruction(it) }
            .forEach { cpu.runInstruction(it) }

        return cpu.signalStrengths.sum()
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private class CPU {

    private var cycle = 0
    private var register = 1
    val signalStrengths = mutableListOf<Int>()

    private fun tick() {
        cycle++
        if ((cycle - 20) % 40 == 0) {
            signalStrengths.add(cycle * register)
        }
    }

    fun parseInstruction(line: String): () -> Unit {
        when {
            line.startsWith("noop") -> return { tick() }
            line.startsWith("addx ") -> return {
                tick()
                tick()
                register += line.substringAfter(" ").toInt()
            }

            else -> throw Exception("Unknown instruction: $line")
        }
    }

    fun runInstruction(instruction: () -> Unit) {
        run(instruction)
    }


}
