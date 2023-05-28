package aoc.years.year2021

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day09Test {

    private val day = Day09()
    private val testInput = listOf(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678",
    )

    @Test
    fun testPart2() {
        assertEquals(1134, day.solvePart2(testInput))
    }

}