package aoc.years.year2023

import aoc.Day
import kotlin.math.max

@Year2023
class Day02 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val bagConfiguration = BagConfiguration(12, 13, 14)

        return input.map { Game.of(it) }
            .filter { it.validGame(bagConfiguration) }
            .sumOf { it.gameId }
    }

    override fun solvePart2(input: List<String>): Any {
        return input.map { Game.of(it) }
            .map { it.minimalBagConfiguration() }
            .sumOf { it.power() }
    }

}

private data class Game(val gameId: Int, val bagReveals: List<BagConfiguration>) {
    companion object {
        private val gameIdInput = "Game (\\d+)".toRegex()

        fun of(line: String): Game {
            val (gameId) = gameIdInput.find(line)!!.destructured

            val bagConfigurations = line.substringAfter(":")
                .split(";")
                .map { BagConfiguration.of(it) }

            return Game(gameId.toInt(), bagConfigurations)
        }
    }

    fun validGame(bagConfiguration: BagConfiguration): Boolean {
        return this.bagReveals.all { it.validBadReveal(bagConfiguration) }
    }

    fun minimalBagConfiguration(): BagConfiguration {
        var (maxRedCubes, maxGreenCubes, maxBlueCubes) = listOf(0, 0, 0)
        this.bagReveals.forEach {
            maxRedCubes = max(maxRedCubes, it.redCubes)
            maxGreenCubes = max(maxGreenCubes, it.greenCubes)
            maxBlueCubes = max(maxBlueCubes, it.blueCubes)
        }
        return BagConfiguration(maxRedCubes, maxGreenCubes, maxBlueCubes)
    }
}

private data class BagConfiguration(val redCubes: Int, val greenCubes: Int, val blueCubes: Int) {
    companion object {
        private val redCubesInput = "(\\d+) red".toRegex()
        private val greenCubesInput = "(\\d+) green".toRegex()
        private val blueCubesInput = "(\\d+) blue".toRegex()
        fun of(line: String): BagConfiguration {
            val redCubes = redCubesInput.find(line)
            val greenCubes = greenCubesInput.find(line)
            val blueCubes = blueCubesInput.find(line)
            return BagConfiguration(
                redCubes?.groupValues?.last()?.toInt() ?: 0,
                greenCubes?.groupValues?.last()?.toInt() ?: 0,
                blueCubes?.groupValues?.last()?.toInt() ?: 0
            )
        }
    }

    fun validBadReveal(bagConfiguration: BagConfiguration): Boolean {
        return this.redCubes <= bagConfiguration.redCubes
                && this.greenCubes <= bagConfiguration.greenCubes
                && this.blueCubes <= bagConfiguration.blueCubes
    }

    fun power(): Int {
        return redCubes * greenCubes * blueCubes
    }
}
