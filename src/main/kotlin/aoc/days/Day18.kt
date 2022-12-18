package aoc.days

import aoc.Day
import kotlin.math.absoluteValue

class Day18 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val lavaDroplet = LavaDroplet.of(input)

        return lavaDroplet.countExposedSides()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
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

    private fun Triple<Int, Int, Int>.countExposedSides(): Int {
        return 6 - this.countAdjacentCubesX() - this.countAdjacentCubesY() - this.countAdjacentCubesZ()
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

}
