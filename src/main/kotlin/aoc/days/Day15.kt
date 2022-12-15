package aoc.days

import aoc.Day
import kotlin.math.absoluteValue

private val INPUT_LINE = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()

class Day15 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val sensors = input
            .map { SensorBeacon.of(it) }

        return (-10_000_000..10_000_000).count { x ->
            sensors.any { it.withinRange(Pair(x, 2_000_000)) }
        }
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private data class SensorBeacon(val sensor: Pair<Int, Int>, val beacon: Pair<Int, Int>, var range: Int = 0) {
    init {
        range = manhattan(sensor, beacon)
    }

    companion object {
        fun of(line: String): SensorBeacon {
            val (s1, s2, b1, b2) = INPUT_LINE.find(line)!!.destructured
            return SensorBeacon(Pair(s1.toInt(), s2.toInt()), Pair(b1.toInt(), b2.toInt()))
        }
    }

    fun withinRange(coordinate: Pair<Int, Int>): Boolean {
        return manhattan(sensor, coordinate) <= range && coordinate != beacon
    }
}

private fun manhattan(first: Pair<Int, Int>, second: Pair<Int, Int>): Int {
    return first.first.minus(second.first).absoluteValue.plus(first.second.minus(second.second).absoluteValue)
}
