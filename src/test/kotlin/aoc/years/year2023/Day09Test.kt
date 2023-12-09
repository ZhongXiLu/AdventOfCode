package aoc.years.year2023

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day09Test {

    @Test
    fun testPart1() {
        val day = Day09()
        assertEquals(
            114, day.solvePart1(
                listOf(
                    "0 3 6 9 12 15",
                    "1 3 6 10 15 21",
                    "10 13 16 21 30 45",
                )
            )
        )
    }

}
