package aoc.years.year2023

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day08Test {

    @Test
    fun testPart8() {
        val day = Day08()
        assertEquals(
                6L, day.solvePart2(
                listOf(
                        "LR",
                        "",
                        "11A = (11B, XXX)",
                        "11B = (XXX, 11Z)",
                        "11Z = (11B, XXX)",
                        "22A = (22B, XXX)",
                        "22B = (22C, 22C)",
                        "22C = (22Z, 22Z)",
                        "22Z = (22B, 22B)",
                        "XXX = (XXX, XXX)",
                )
        )
        )
    }

}
