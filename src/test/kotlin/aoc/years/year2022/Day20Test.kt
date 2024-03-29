package aoc.years.year2022

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day20Test {

    private val day = Day20()
    private val testInput = listOf(
        "1",
        "2",
        "-3",
        "3",
        "-2",
        "0",
        "4",
    )

    @Test
    fun testPart1() {
        assertEquals(3L, day.solvePart1(testInput))
    }

}