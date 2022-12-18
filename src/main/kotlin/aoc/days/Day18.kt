package aoc.days

import aoc.Day
import kotlin.math.absoluteValue

class Day18 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return LavaDroplet.of(input)
            .countExposedSides()
    }

    override fun solvePart2(input: List<String>): Any {
        return LavaDroplet.of(input)
            .countExposedExternalSides()
    }

}

private class LavaDroplet(val lavaDroplets: List<Triple<Int, Int, Int>>) {
    val lavaDroplet: List<List<List<Char>>>

    companion object {
        fun of(input: List<String>): LavaDroplet {
            val lavaDroplets = input
                .map { it.split(",") }
                .map { (x, y, z) -> Triple(x.toInt(), y.toInt(), z.toInt()) }
            return LavaDroplet(lavaDroplets)
        }
    }

    init {
        val largestX = lavaDroplets.maxOf { it.first }
        val largestY = lavaDroplets.maxOf { it.second }
        val largestZ = lavaDroplets.maxOf { it.third }

        lavaDroplet = (0..largestX).map {
            (0..largestY).map {
                (0..largestZ).map {
                    '.'
                }.toMutableList()
            }.toMutableList()
        }.toMutableList()

        lavaDroplets.forEach { lavaDroplet[it.first][it.second][it.third] = '#' }
    }

    fun countExposedSides(): Int {
        return lavaDroplets.sumOf { it.countExposedSides() }
    }

    fun countExposedExternalSides(): Int {
        return lavaDroplets.sumOf { it.countExposedExternalSides() }
    }

    private fun Triple<Int, Int, Int>.countExposedSides(): Int {
        return 6 - countAdjacentCubesX() - countAdjacentCubesY() - countAdjacentCubesZ()
    }

    private fun Triple<Int, Int, Int>.countAdjacentCubesX(): Int {
        return lavaDroplets.count { it.first.minus(this.first).absoluteValue == 1 && it.second == this.second && it.third == this.third }
    }

    private fun Triple<Int, Int, Int>.countAdjacentCubesY(): Int {
        return lavaDroplets.count { it.second.minus(this.second).absoluteValue == 1 && it.first == this.first && it.third == this.third }
    }

    private fun Triple<Int, Int, Int>.countAdjacentCubesZ(): Int {
        return lavaDroplets.count { it.third.minus(this.third).absoluteValue == 1 && it.first == this.first && it.second == this.second }
    }

    private fun Triple<Int, Int, Int>.countExposedExternalSides(): Int {
        return this.getSides().count { it.isConnectedToOutside(mutableSetOf(it)) }
    }

    private fun Triple<Int, Int, Int>.isConnectedToOutside(visited: MutableSet<Triple<Int, Int, Int>>): Boolean {
        if (this.first !in lavaDroplet.indices
            || this.second !in lavaDroplet.first().indices
            || this.third !in lavaDroplet.first().first().indices
        ) {
            return true     // reached border
        }

        if (lavaDroplet[this.first][this.second][this.third] == '#') {
            return false
        }

        return this.getSides()
            .filter { it !in visited }
            .also { visited.addAll(it) }
            .any { it.isConnectedToOutside(visited) }
    }

    private fun Triple<Int, Int, Int>.getSides(): List<Triple<Int, Int, Int>> {
        return listOf(
            Triple(this.first - 1, this.second, this.third), Triple(this.first + 1, this.second, this.third),
            Triple(this.first, this.second - 1, this.third), Triple(this.first, this.second + 1, this.third),
            Triple(this.first, this.second, this.third - 1), Triple(this.first, this.second, this.third + 1)
        )
    }
}
