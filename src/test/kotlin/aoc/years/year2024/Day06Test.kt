package aoc.years.year2024

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day06Test {

    @Test
    fun testPart1() {
        val day = Day06()
        assertEquals(
            41, day.solvePart1(
                listOf(
                    "....#.....",
                    ".........#",
                    "..........",
                    "..#.......",
                    ".......#..",
                    "..........",
                    ".#..^.....",
                    "........#.",
                    "#.........",
                    "......#...",
                )
            )
        )
    }

}