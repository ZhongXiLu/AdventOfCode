package aoc.years.year2024

import aoc.Day
import java.util.*
import kotlin.collections.List
import kotlin.collections.MutableSet
import kotlin.collections.filter
import kotlin.collections.flatten
import kotlin.collections.indices
import kotlin.collections.listOf
import kotlin.collections.map
import kotlin.collections.mutableListOf
import kotlin.collections.mutableSetOf
import kotlin.math.abs

@Year2024
class Day08 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Map.of(input).getAntinodes().size
    }

    override fun solvePart2(input: List<String>): Any {
        return Map.of(input).getAntinodesWithResonance().size
    }

}

private class Map(val map: List<List<String>>) {
    companion object {
        fun of(input: List<String>): Map {
            val map = input.map { it.split("").filter(String::isNotBlank) }
            return Map(map)
        }
    }

    fun getAntinodes(): MutableSet<Pair<Int, Int>> {
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        for (x in map.indices) {
            for (y in map[x].indices) {
                if (map[x][y] != ".") {
                    antinodes.addAll(findAntinodes(Pair(x, y), map[x][y]))
                }
            }
        }

        return antinodes
    }

    private fun findAntinodes(antenna: Pair<Int, Int>, frequency: String): List<Pair<Int, Int>> {
        return findAntennas(antenna, frequency)
            .map {
                listOf(it.getAntiNode(antenna), antenna.getAntiNode(it))
                    .filter { optional -> optional.isPresent }
                    .map { optional -> optional.get() }
            }
            .flatten()
    }

    private fun findAntennas(original: Pair<Int, Int>, frequency: String): List<Pair<Int, Int>> {
        val antennas = mutableListOf<Pair<Int, Int>>()

        for (x in map.indices) {
            for (y in map[x].indices) {
                if (x == original.first && y == original.second) {
                    continue
                }

                if (map[x][y] == frequency) {
                    antennas.add(Pair(x, y))
                }
            }
        }

        return antennas
    }

    private fun Pair<Int, Int>.getAntiNode(other: Pair<Int, Int>): Optional<Pair<Int, Int>> {
        val distanceX = abs(this.first - other.first)
        val distanceY = abs(this.second - other.second)

        val x = if (this.first > other.first) this.first + distanceX else this.first - distanceX
        val y = if (this.second > other.second) this.second + distanceY else this.second - distanceY

        if (x in map.indices && y in map[x].indices) {
            return Optional.of(Pair(x, y))
        }

        return Optional.empty()
    }

    fun getAntinodesWithResonance(): MutableSet<Pair<Int, Int>> {
        val antinodes = mutableSetOf<Pair<Int, Int>>()

        for (x in map.indices) {
            for (y in map[x].indices) {
                if (map[x][y] != ".") {
                    antinodes.addAll(findAntinodesWithResonance(Pair(x, y), map[x][y]))
                }
            }
        }

        return antinodes
    }

    private fun findAntinodesWithResonance(antenna: Pair<Int, Int>, frequency: String): List<Pair<Int, Int>> {
        return findAntennas(antenna, frequency)
            .map {
                listOf(it.getAntiNodesWithResonance(antenna), antenna.getAntiNodesWithResonance(it)).flatten()
            }
            .flatten()
    }

    private fun Pair<Int, Int>.getAntiNodesWithResonance(other: Pair<Int, Int>): List<Pair<Int, Int>> {
        val antinodes = mutableListOf<Pair<Int, Int>>()

        val distanceX = abs(this.first - other.first)
        val distanceY = abs(this.second - other.second)

        val goRight = this.first > other.first
        val goDown = this.second > other.second

        var x = if (goRight) this.first + distanceX else this.first - distanceX
        var y = if (goDown) this.second + distanceY else this.second - distanceY

        while (x in map.indices && y in map[x].indices) {
            val antinode = Pair(x, y)
            antinodes.add(antinode)
            x = if (goRight) antinode.first + distanceX else antinode.first - distanceX
            y = if (goDown) antinode.second + distanceY else antinode.second - distanceY
        }

        return antinodes.plus(this).plus(other)
    }

}
