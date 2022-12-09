package aoc.days

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day09Test {

    @Test
    fun testPart1() {
        val day = Day09()
        assertEquals(
            13, day.solvePart1(
                listOf(
                    "R 4",
                    "U 4",
                    "L 3",
                    "D 1",
                    "R 4",
                    "D 1",
                    "L 5",
                    "R 2"
                )
            )
        )
    }

}