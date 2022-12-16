package aoc.days

import aoc.Day
import java.util.*

private val INPUT_LINE = "Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z ,]+)".toRegex()

class Day16 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val valves = input.map { Valve.of(it) }
        val startValve = valves.first { it.name == "AA" }

        val queue = PriorityQueue { v1: Valve, v2: Valve -> v2.pressure - v1.pressure }
        val visitedValves = mutableSetOf(startValve)

        queue.add(startValve)

        while (queue.isNotEmpty()) {
            val nextValve = queue.poll()
            println("Visiting $nextValve")
            val connectedValves = valves.getValves(nextValve.connectedValves)

            connectedValves
                .filter { it !in visitedValves }
                .forEach {
                    it.pressure = nextValve.pressure + it.rate
                    visitedValves.add(it)
                    queue.add(it)
                }
        }

        return ""
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private data class Valve(val name: String, val rate: Int, val connectedValves: List<String>, var pressure: Int = 0) {
    companion object {
        fun of(line: String): Valve {
            val (name, rate, connectedValves) = INPUT_LINE.find(line)!!.destructured
            return Valve(name, rate.toInt(), connectedValves.split(", "))
        }
    }
}

private fun List<Valve>.getValves(connectedValves: List<String>): List<Valve> {
    return this.filter { connectedValves.contains(it.name) }
}
