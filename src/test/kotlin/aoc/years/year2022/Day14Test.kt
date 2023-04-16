package aoc.years.year2022

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day14Test {

    private val testInput = listOf(
        "498,4 -> 498,6 -> 496,6",
        "503,4 -> 502,4 -> 502,9 -> 494,9"
    )

    @Test
    fun testPart1() {
        val day = Day14()
        assertEquals(24, day.solvePart1(testInput))
    }

}