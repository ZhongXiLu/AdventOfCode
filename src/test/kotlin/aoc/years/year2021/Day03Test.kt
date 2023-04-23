package aoc.years.year2021

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day03Test {

    private val day = Day03()
    private val testInput = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"
    )

    @Test
    fun testPart2() {
        assertEquals(230, day.solvePart2(testInput))
    }

}