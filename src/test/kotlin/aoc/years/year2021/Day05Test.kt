package aoc.years.year2021

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day05Test {

    private val day = Day05()
    private val testInput = listOf(
        "0,9 -> 5,9",
        "8,0 -> 0,8",
        "9,4 -> 3,4",
        "2,2 -> 2,1",
        "7,0 -> 7,4",
        "6,4 -> 2,0",
        "0,9 -> 2,9",
        "3,4 -> 1,4",
        "0,0 -> 8,8",
        "5,5 -> 8,2",
    )

    @Test
    fun testPart2() {
        assertEquals(12, day.solvePart2(testInput))
    }

}