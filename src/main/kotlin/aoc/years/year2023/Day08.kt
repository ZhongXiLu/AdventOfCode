package aoc.years.year2023

import aoc.Day
import kotlin.math.abs

@Year2023
class Day08 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.getMap().getNrOfStepsToEnd(input.first(), "AAA") { it != "ZZZ" }
    }

    override fun solvePart2(input: List<String>): Any {
        val map = input.getMap()

        val startNodes = map.filterKeys { it.endsWith("A") }.map { it.key }
        val distancesToEnd = startNodes.fold(listOf<Int>()) { distances, node ->
            return@fold distances.plus(map.getNrOfStepsToEnd(input.first(), node) { !it.endsWith("Z") })
        }

        return lcm(distancesToEnd)
    }

}

private fun List<String>.getMap(): MutableMap<String, Pair<String, String>> {
    return this.drop(2).fold(mutableMapOf()) { map, line ->
        val node = line.substring(0, 3)
        val left = line.substring(7, 10)
        val right = line.substring(12, 15)
        return@fold map.plus(Pair(node, Pair(left, right))).toMutableMap()
    }
}

private fun MutableMap<String, Pair<String, String>>.getNrOfStepsToEnd(instructions: String, startNode: String, endCondition: (String) -> Boolean): Int {
    var currentInstruction = 0
    var steps = 0
    var currentNode = startNode

    while (endCondition(currentNode)) {
        if (currentInstruction == instructions.length) {
            currentInstruction = 0
        }

        currentNode = if (instructions[currentInstruction] == 'L') this[currentNode]!!.first else this[currentNode]!!.second

        currentInstruction++
        steps++
    }

    return steps
}

fun lcm(numbers: List<Int>): Long {
    fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long): Long = if (a == 0L || b == 0L) 0 else abs(a * b) / gcd(a, b)
    return numbers.map { it.toLong() }.fold(1L) { acc, num -> lcm(acc, num) }
}
