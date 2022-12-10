package aoc.days

import aoc.Day

class Day10 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val cpu = CPU()

        input
            .map(cpu::parseInstruction)
            .forEach(cpu::runInstruction)

        return cpu.signalStrengths.sum()
    }

    override fun solvePart2(input: List<String>): Any {
        val cpu = CPU()

        input
            .map(cpu::parseInstruction)
            .forEach(cpu::runInstruction)

        return "\n" + cpu.crt
            .chunked(40)
            .joinToString("\n") { it.joinToString("") }
    }

}

private class CPU {

    private var cycle = 0
    private var register = 1
    val signalStrengths = mutableListOf<Int>()
    val crt = mutableListOf<String>()

    private fun tick() {
        if ((cycle % 40) in (IntRange(register - 1, register + 1))) crt.add("#") else crt.add(".")

        cycle++

        if ((cycle - 20) % 40 == 0) {
            signalStrengths.add(cycle * register)
        }
    }

    fun parseInstruction(line: String): () -> Unit {
        when {
            line.startsWith("noop") -> return { tick() }
            line.startsWith("addx ") -> return {
                repeat(2) { tick() }
                register += line.substringAfter(" ").toInt()
            }

            else -> throw Exception("Unknown instruction: $line")
        }
    }

    fun runInstruction(instruction: () -> Unit) {
        run(instruction)
    }


}
