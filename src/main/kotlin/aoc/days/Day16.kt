package aoc.days

import aoc.Day
import java.util.*

private val INPUT_LINE = "Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z ,]+)".toRegex()

class Day16 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val (startValve, valveMap) = initializeValves(input)

        // Calculate every possible path from the start valve to the valves with non-zero flow rate
        // and retrieve the maximum possible flow.
        return startValve.getFlows(valveMap, 30, mutableSetOf(startValve.name)).maxOf { it.first }
    }

    override fun solvePart2(input: List<String>): Any {
        val (startValve, valveMap, bitMap) = initializeValves(input)

        val flows = startValve.getFlows(valveMap, 26, mutableSetOf(startValve.name))
            .map { Pair(it.first, it.second.toBitSet(bitMap)) }
        return flows
            .parallelStream()
            .map { flow ->
                val nonOverlappingFlows = flows.filter { otherFlow -> !flow.second.intersects(otherFlow.second) }
                return@map flow.first.plus(if (nonOverlappingFlows.isNotEmpty()) nonOverlappingFlows.maxOf { it.first } else 0)
            }
            .max { f1, f2 -> f1 - f2 }
            .get()
    }

    private fun initializeValves(input: List<String>): Triple<Valve, MutableMap<String, Valve>, Map<String, Int>> {
        val valves = input.map { Valve.of(it) }
        val startValve = valves.first { it.name == "AA" }
        // Create a valves map, so we could easily retrieve a Valve object based on its name
        // Otherwise, if we reference Valve objects in Valve objects themselves,
        // we get StackOverflow errors because of circular dependencies.
        val valveMap = valves.fold(mutableMapOf<String, Valve>()) { map, valve ->
            map.plus(Pair(valve.name, valve)).toMutableMap()
        }

        // Pre-calculate the minimum distance between the start valve and any other valve with non-zero flow rate.
        // Calculated using BFS.
        val valvesWithFlowRate = valves.filter { it.rate > 0 }.plus(startValve)
        valvesWithFlowRate.forEach { valve ->
            valvesWithFlowRate.forEach { otherValve ->
                valve.addDistanceToValve(otherValve.name, valves.calculateDistanceTo(valve, otherValve))
            }
        }

        // Create a bitmap to create bitsets later on to speed up comparisons of visited Valves
        val bitMap = valvesWithFlowRate.foldIndexed(mutableMapOf<String, Int>()) { index, map, valve ->
            map.plus(Pair(valve.name, index)).toMutableMap()
        }.minus("AA")

        return Triple(startValve, valveMap, bitMap)
    }

}

private data class Valve(
    val name: String,
    val rate: Int,
    val connectedValves: List<String>,
    var distanceToOtherValves: MutableMap<String, Int> = mutableMapOf()
) {
    companion object {
        fun of(line: String): Valve {
            val (name, rate, connectedValves) = INPUT_LINE.find(line)!!.destructured
            return Valve(name, rate.toInt(), connectedValves.split(", "))
        }
    }

    fun addDistanceToValve(valve: String, distance: Int) {
        distanceToOtherValves[valve] = distance
    }

    fun getFlows(
        valveMap: Map<String, Valve>,
        minutesLeft: Int,
        visited: MutableSet<String>,
        currentRate: Int = 0
    ): List<Pair<Int, MutableSet<String>>> {
        val unvisitedAndReachableValves = distanceToOtherValves
            .filter { (valve, distance) -> valve !in visited && minutesLeft + 2 >= distance }
        //                                                                   ^^^ +1 for turning on current valve
        //                                                                   ^^^ +1 for turning on the next valve

        if (unvisitedAndReachableValves.isNotEmpty()) {
            return unvisitedAndReachableValves
                .map { (valve, distance) ->
                    val newFlow = (currentRate + rate) * distance + currentRate
                    val paths = valveMap[valve]!!.getFlows(
                        valveMap,

                        (minutesLeft - distance).minus(if (name == "AA") 0 else 1),
                        //                             ^^^^^^^^^^^^^^^^^ No need to turn on the start valve

                        visited.plus(valve).toMutableSet(),
                        (currentRate + rate)
                    )
                    return@map paths.map { Pair(it.first + newFlow, it.second) }
                }
                .flatten()
        }

        // All valves open or no time left to open any more valves  => open current valve and wait until time runs out
        return listOf(Pair(((minutesLeft - 1) * (currentRate + rate)) + currentRate, visited.toMutableSet()))
    }

}


private fun List<Valve>.calculateDistanceTo(source: Valve, destination: Valve): Int {
    val nextValves = mutableListOf(Pair(source, 0))
    val visitedValves = mutableSetOf(source)

    while (nextValves.isNotEmpty()) {
        val (nextValve, distance) = nextValves.removeFirst()
        if (nextValve.name == destination.name) return distance

        getValves(nextValve.connectedValves)
            .filter { it !in visitedValves }
            .forEach {
                visitedValves.add(it)
                nextValves.add(Pair(it, distance + 1))
            }
    }

    return 0
}

private fun List<Valve>.getValves(connectedValves: List<String>): List<Valve> {
    return this.filter { connectedValves.contains(it.name) }
}

private fun MutableSet<String>.toBitSet(bitMap: Map<String, Int>): BitSet {
    val bitSet = BitSet(bitMap.size)
    this.filter { it != "AA" }
        .filter { it in bitMap }
        .forEach { bitSet.set(bitMap[it]!!, true) }
    return bitSet
}
