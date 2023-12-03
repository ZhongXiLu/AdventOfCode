package aoc.years.year2023

import aoc.Day

@Year2023
class Day03 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Engine(input)
            .getPartNumbers()
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        return Engine(input)
            .getTotalGearRatio()
    }

}

class Engine(private val schematic: List<String>) {

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

    fun getTotalGearRatio(): Int {
        return schematic.mapIndexed { x, line ->
            line.mapIndexed { y, char -> if (char == '*') getGearRatio(x, y) else 0 }.sum()
        }.sum()
    }

    private fun Char.isSymbol(): Boolean {
        return !this.isDigit() && this != '.'
    }

    private fun getGearRatio(x: Int, y: Int): Int {
        val adjacentParts = adjacentPositions(x, y)
            .map { getPartNumber(it.first, it.second) }
            .filter { it != -1 }
            .distinct()
        return if (adjacentParts.size == 2) adjacentParts[0] * adjacentParts[1] else 0
    }

    private fun getPartNumber(x: Int, y: Int): Int {
        if (this.schematic[x][y].isDigit()) {
            val leftPart = ((y - 1) downTo 0)
                .takeWhile { this.schematic[x][it].isDigit() }
                .map { this.schematic[x][it] }
                .reversed()
                .fold("") { leftPart, char -> leftPart.plus(char) }
            val rightPart = ((y + 1)..(this.schematic.first().length - 1))
                .takeWhile { this.schematic[x][it].isDigit() }
                .map { this.schematic[x][it] }
                .fold("") { rightPart, char -> rightPart.plus(char) }
            return (leftPart + this.schematic[x][y] + rightPart).toInt()
        } else {
            return -1
        }
    }

    private fun adjacentPositions(x: Int, y: Int): List<Pair<Int, Int>> {
        return listOf(
            Pair(x - 1, y - 1), Pair(x - 1, y), Pair(x - 1, y + 1),
            Pair(x, y - 1), Pair(x, y + 1),
            Pair(x + 1, y - 1), Pair(x + 1, y), Pair(x + 1, y + 1),
        ).filter { it.first in this.schematic.indices && it.second in this.schematic.first().indices }
    }
}

