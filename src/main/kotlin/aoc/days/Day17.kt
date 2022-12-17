package aoc.days

import aoc.Day

class Day17 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val chamber = Chamber(7, 2022 * 4, JetPattern.of(input.first()), RockPattern())
        return chamber.getHeightAfterDroppingRocks(2022)
    }

    override fun solvePart2(input: List<String>): Any {
        val chamber = Chamber(7, 2022 * 4, JetPattern.of(input.first()), RockPattern())
        return chamber.getHeightAfterDroppingRocks(1_000_000_000_000)
    }

}

// God amongst gods class
private class Chamber(val width: Int, height: Int, val jetPattern: JetPattern, val rockPattern: RockPattern) {
    val chamber: List<CharArray> = listOf("#".repeat(width + 2).toCharArray()).plus(
        (0..height).map { "#".toCharArray().plus(".".repeat(width).toCharArray()).plus("#".toCharArray()) }
    )
    val fallenRockRows: MutableSet<String> = mutableSetOf()
    val fallenRockRowsMap: MutableMap<String, Pair<Int, Int>> = mutableMapOf()
    var rocksDropped = 0

    fun getHeightAfterDroppingRocks(amount: Long): Long {
        // Drop some initial rocks, for some reason, the cycle starts only after x amount of rocks
        repeat(100) {
            dropRock(rockPattern.next())
        }

        // Drop rocks until we have found a repeating cycle
        while (!foundRepeatingFallenRockPattern()) {
            dropRock(rockPattern.next())
        }

        // Do some math calculations
        val last100RockRows = getLastFallenRockRows(100).map { it.joinToString("") }.joinToString("\n")
        val (heightDuringLastCycle, rocksDuringLastCycle) = fallenRockRowsMap.get(last100RockRows)!!
        val rocksDuringCycle = rocksDropped - rocksDuringLastCycle
        val heightDuringCycle = getCurrentHeight() - heightDuringLastCycle
        val remainingCycles = amount.minus(rocksDropped).div(rocksDuringCycle)
        val remainingRocksToDrop = amount.minus(rocksDropped).mod(rocksDuringCycle)

        // Drop the remaining rocks
        repeat(remainingRocksToDrop) {
            dropRock(rockPattern.next())
        }

        // Use the magic math calculations to calculate the final height
        return getCurrentHeight() + heightDuringCycle * remainingCycles
    }

    private fun foundRepeatingFallenRockPattern(): Boolean {
        val last100RockRows = getLastFallenRockRows(100).map { it.joinToString("") }.joinToString("\n")
        if (last100RockRows in fallenRockRows) {
            return true
        } else {
            fallenRockRows.add(last100RockRows)
            fallenRockRowsMap.put(last100RockRows, Pair(getCurrentHeight(), rocksDropped))
            return false
        }
    }

    private fun getLastFallenRockRows(rows: Int): List<CharArray> {
        val lastFallenRockRow = getCurrentHeight()
        return (lastFallenRockRow downTo lastFallenRockRow - rows + 1)
            .map { chamber[it] }
    }

    private fun dropRock(rock: List<List<Char>>) {
        rocksDropped++
        // Place rock at starting position
        var rockPosition = Pair(getDroppingHeight(rock), 3)

        while (canPlaceRockAt(rockPosition, rock)) {
            // Jet pushes rock
            val pushedRockPosition = Pair(rockPosition.first, rockPosition.second + jetPattern.next())
            if (canPlaceRockAt(pushedRockPosition, rock)) {
                rockPosition = pushedRockPosition
            }

            // Rocks drops one row down
            rockPosition = Pair(rockPosition.first - 1, rockPosition.second)
        }

        placeRockAt(Pair(rockPosition.first + 1, rockPosition.second), rock)
    }

    private fun placeRockAt(position: Pair<Int, Int>, rock: List<List<Char>>) {
        rock.forEachIndexed { x, row ->
            row.forEachIndexed { y, rockChar ->
                if (rockChar == '#') chamber[position.first - x][position.second + y] = rockChar
            }
        }
    }

    private fun canPlaceRockAt(position: Pair<Int, Int>, rock: List<List<Char>>): Boolean {
        return rock.mapIndexed { x, row ->
            row.mapIndexed { y, rockChar ->
                (rockChar == '#' && chamber[position.first - x][position.second + y] != '#') || rockChar == '.'
            }.all { it }
        }.all { it }
    }

    private fun getDroppingHeight(rock: List<List<Char>>): Int {
        return getCurrentHeight()
            .plus(3)
            .plus(rock.size)
    }

    private fun getCurrentHeight() = chamber
        .takeWhile { row -> row.count { it == '#' } != 2 }
        .count()
        .minus(1)

    override fun toString(): String {
        return chamber
            .reversed()
            .joinToString("\n") { it.joinToString("") }
    }

}

private class JetPattern(val pattern: CharArray) {
    private var current = 0

    companion object {
        fun of(line: String): JetPattern {
            return JetPattern(line.trim().toCharArray())
        }
    }

    fun next(): Int {
        if (current == pattern.size) current = 0
        return if (pattern[current++] == '<') -1 else 1
    }
}

private class RockPattern {
    private val rocks = listOf(
        listOf(
            listOf('#', '#', '#', '#')
        ),
        listOf(
            listOf('.', '#', '.'),
            listOf('#', '#', '#'),
            listOf('.', '#', '.')
        ),
        listOf(
            listOf('.', '.', '#'),
            listOf('.', '.', '#'),
            listOf('#', '#', '#')
        ),
        listOf(
            listOf('#'),
            listOf('#'),
            listOf('#'),
            listOf('#')
        ),
        listOf(
            listOf('#', '#'),
            listOf('#', '#')
        ),
    )
    private var current = 0

    fun next(): List<List<Char>> {
        if (current == rocks.size) current = 0
        return rocks[current++]
    }
}
