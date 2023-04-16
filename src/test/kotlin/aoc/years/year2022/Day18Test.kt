package aoc.years.year2022

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day18Test {

    private val day = Day18()
    private val testInput = listOf(
        "2,2,2",
        "1,2,2",
        "3,2,2",
        "2,1,2",
        "2,3,2",
        "2,2,1",
        "2,2,3",
        "2,2,4",
        "2,2,6",
        "1,2,5",
        "3,2,5",
        "2,1,5",
        "2,3,5",
    )

    @Test
    fun testPart2() {
        assertEquals(58, day.solvePart2(testInput))
    }

}