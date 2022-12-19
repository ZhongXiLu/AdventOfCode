package aoc.days

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day19Test {

    private val day = Day19()
    private val testInput = listOf(
        "Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.",
        "Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.",
    )

    @Test
    fun testPart1() {
        assertEquals(33, day.solvePart1(testInput))
    }

}