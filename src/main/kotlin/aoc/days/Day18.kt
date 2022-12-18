package aoc.days

import aoc.Day
import kotlin.math.absoluteValue

class Day18 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val cubes = input
            .map { it.split(",") }
            .map { (x, y, z) -> Triple(x.toInt(), y.toInt(), z.toInt()) }

        return cubes.sumOf { it.countExposedSides(cubes) }
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private fun Triple<Int, Int, Int>.countExposedSides(cubes: List<Triple<Int, Int, Int>>): Int {
    return 6 - this.countAdjacentCubesX(cubes) - this.countAdjacentCubesY(cubes) - this.countAdjacentCubesZ(cubes)
}

private fun Triple<Int, Int, Int>.countAdjacentCubesX(cubes: List<Triple<Int, Int, Int>>): Int {
    return cubes.count { it.first.minus(this.first).absoluteValue == 1 && it.second == this.second && it.third == this.third }
}

private fun Triple<Int, Int, Int>.countAdjacentCubesY(cubes: List<Triple<Int, Int, Int>>): Int {
    return cubes.count { it.second.minus(this.second).absoluteValue == 1 && it.first == this.first && it.third == this.third }
}

private fun Triple<Int, Int, Int>.countAdjacentCubesZ(cubes: List<Triple<Int, Int, Int>>): Int {
    return cubes.count { it.third.minus(this.third).absoluteValue == 1 && it.first == this.first && it.second == this.second }
}
