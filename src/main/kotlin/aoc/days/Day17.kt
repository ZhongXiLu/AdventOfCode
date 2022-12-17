package aoc.days

import aoc.Day

class Day17 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val chamber = Chamber(7, 2022 * 4, JetPattern.of(input.first()))
        val rockPattern = RockPattern()

        repeat(2022) { chamber.dropRock(rockPattern.next()) }

        return chamber.getHeightOfFallenRocks()
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private class Chamber(val width: Int, height: Int, val jetPattern: JetPattern) {
    val chamber: List<CharArray> = listOf("#".repeat(width + 2).toCharArray()).plus(
        (0..height).map { "#".toCharArray().plus(".".repeat(width).toCharArray()).plus("#".toCharArray()) }
    )

    fun dropRock(rock: List<List<Char>>) {
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

    fun getHeightOfFallenRocks(): Int {
        return chamber
            .takeWhile { row -> row.count { it == '#' } != 2 }
            .count()
            .minus(1)
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
        return chamber
            .takeWhile { row -> row.count { it == '#' } != 2 }
            .count()
            .plus(3)
            .plus(rock.size - 1)
    }

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
