package aoc.years.year2023

import aoc.Day

@Year2023
class Day03 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Engine.of(input)
            .getPartNumbers()
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

class Engine(val schematic: List<String>) {
    companion object {
        fun of(input: List<String>): Engine {
            return Engine(input)
        }
    }

    fun getPartNumbers(): List<Int> {
        return schematic.mapIndexed { x, line ->
            val partNumbers = mutableListOf<Int>()
            var currentPartNumber = ""
            var isCurrentPartEnginePart = false
            for ((y, char) in line.withIndex()) {
                if (char.isDigit()) {
                    currentPartNumber += char
                    if (adjacentPositions(x, y).any { this.schematic[it.first][it.second].isSymbol() }) {
                        isCurrentPartEnginePart = true
                    }
                } else {
                    if (isCurrentPartEnginePart) {
                        partNumbers.add(currentPartNumber.toInt())
                    }
                    currentPartNumber = ""
                    isCurrentPartEnginePart = false
                }
            }
            if (isCurrentPartEnginePart) {
                partNumbers.add(currentPartNumber.toInt())
            }
            return@mapIndexed partNumbers
        }.flatten()
    }

    private fun adjacentPositions(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x - 1, y - 1), Pair(x - 1, y), Pair(x - 1, y + 1),
            Pair(x, y - 1), Pair(x, y + 1),
            Pair(x + 1, y - 1), Pair(x + 1, y), Pair(x + 1, y + 1),
        ).filter { it.first in this.schematic.indices && it.second in this.schematic.first().indices }
    }

    private fun Char.isSymbol(): Boolean {
        return !this.isDigit() && this != '.'
    }
}

