package aoc.days

import aoc.Day
import kotlin.math.absoluteValue

private val INPUT_LINE = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)".toRegex()

class Day15 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val sensors = input.map { SensorBeacon.of(it) }

        return (-10_000_000..10_000_000).count { x ->
            sensors.any { it.withinRange(Pair(x, 2_000_000)) }
        }
    }

    override fun solvePart2(input: List<String>): Any {
        val sensors = input.map { SensorBeacon.of(it) }

        return sensors
            .map { it.getPerimeterCoords() }
            .flatten()
            .also { println(it.count()) }
            .first { coords -> sensors.none { it.withinRange(coords) } }
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

    fun withinRange(coords: Pair<Int, Int>): Boolean {
        return manhattan(sensor, coords) <= range && coords != beacon
    }

    fun getPerimeterCoords(): List<Pair<Int, Int>> {
        return (IntRange(sensor.first - range - 1, sensor.first))
            .mapIndexed { index, x -> listOf(Pair(x, sensor.second - index), Pair(x, sensor.second + index)) }
            .flatten()
            .plus(
                ((sensor.first + range + 1) downTo sensor.first)
                    .mapIndexed { index, x -> listOf(Pair(x, sensor.second - index), Pair(x, sensor.second + index)) }
                    .flatten()
            )
    }
}

private fun manhattan(first: Pair<Int, Int>, second: Pair<Int, Int>): Int {
    return first.first.minus(second.first).absoluteValue.plus(first.second.minus(second.second).absoluteValue)
}
