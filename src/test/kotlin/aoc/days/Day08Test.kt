package aoc.days

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day08Test {

    @Test
    fun testPart2() {
        val day = Day08()
        assertEquals(8, day.solvePart2(listOf(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390"
        )))
    }

}