package aoc.years.year2021

import aoc.Day


@Year2021
class Day11 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val cavern = Cavern.of(input)

        repeat(100) {
            cavern.step()
        }

        return cavern.flashCount
    }

    override fun solvePart2(input: List<String>): Any {
        val cavern = Cavern.of(input)

        return generateSequence(0) { it + 1 }
            .takeWhile { cavern.step() }
            .count()
            .plus(1)
    }

}

private data class Cavern(var octopuses: Array<Array<Int>>, var flashCount: Int = 0) {

    companion object {
        fun of(input: List<String>): Cavern {
            val octopuses = input.map { it.map { digit -> digit.digitToInt() }.toTypedArray() }.toTypedArray()
            return Cavern(octopuses)
        }
    }

    fun step(): Boolean {
        incrementAllEnergyLevel()

        var stepFlashCount = 0
        do {
            for (x in this.octopuses.indices) {
                for (y in this.octopuses[x].indices) {
                    if (this.octopuses[x][y] > 9) {
                        this.octopuses[x][y] = 0
                        adjacentOctopuses(x, y).forEach { incrementEnergyLevel(it.first, it.second) }
                        this.flashCount++
                        stepFlashCount++
                    }
                }
            }
        } while (octopusAboutToFlash())

        val allOctopusesHaveFlashed = this.octopuses.flatten().count() != stepFlashCount
        return allOctopusesHaveFlashed
    }

    private fun octopusAboutToFlash(): Boolean {
        return this.octopuses.flatten().any { it > 9 }
    }

    private fun incrementAllEnergyLevel() {
        this.octopuses.forEachIndexed { x, row -> row.indices.forEach { y -> this.octopuses[x][y]++ } }
    }

    private fun incrementEnergyLevel(x: Int, y: Int) {
        if (this.octopuses[x][y] > 0) this.octopuses[x][y]++
    }

    private fun adjacentOctopuses(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x - 1, y - 1), Pair(x - 1, y), Pair(x - 1, y + 1),
            Pair(x, y - 1), Pair(x, y + 1),
            Pair(x + 1, y - 1), Pair(x + 1, y), Pair(x + 1, y + 1),
        ).filter { it.first in this.octopuses.indices && it.second in this.octopuses.first().indices }
    }
}
