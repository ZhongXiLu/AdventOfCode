package aoc.years.year2024

import aoc.Day

@Year2024
class Day03 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.toInstructions().sumOf {
            it.evaluateMul()
        }
    }

    override fun solvePart2(input: List<String>): Any {
        var sum = 0

        val instructions = input.toAllInstructions()
        var enabled = true
        for (instruction in instructions) {
            if (instruction.startsWith("don't")) {
                enabled = false
            } else if (instruction.startsWith("do")) {
                enabled = true
            } else if (enabled) {
                sum += instruction.evaluateMul()
            }
        }

        return sum
    }

}

private fun String.evaluateMul(): Int {
    val instruction = "mul\\((\\d+),(\\d+)\\)".toRegex()
    val (first, second) = instruction.find(this)!!.destructured
    return first.toInt() * second.toInt()
}

private fun List<String>.toInstructions(): List<String> {
    return this.map {
        val instructions = "(mul\\(\\d+,\\d+\\))".toRegex()
        return@map instructions.findAll(it).toList()
    }
        .flatten()
        .map { it.value }
}

private fun List<String>.toAllInstructions(): List<String> {
    return this.map {
        val instructions = "(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don\'t\\(\\))".toRegex()
        return@map instructions.findAll(it).toList()
    }
        .flatten()
        .map { it.value }
}
