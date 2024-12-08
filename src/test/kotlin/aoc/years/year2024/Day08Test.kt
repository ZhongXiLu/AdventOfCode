package aoc.years.year2024

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day08Test {

    @Test
    fun testPart1() {
        val day = Day08()
        assertEquals(
            14, day.solvePart1(
                listOf(
                    "............",
                    "........0...",
                    ".....0......",
                    ".......0....",
                    "....0.......",
                    "......A.....",
                    "............",
                    "............",
                    "........A...",
                    ".........A..",
                    "............",
                    "............",
                )
            )
        )
    }

    @Test
    fun testPart2_basic() {
        val day = Day08()
        assertEquals(
            9, day.solvePart2(
                listOf(
                    "T.........",
                    "...T......",
                    ".T........",
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                    "..........",
                )
            )
        )
    }

    @Test
    fun testPart2() {
        val day = Day08()
        assertEquals(
            34, day.solvePart2(
                listOf(
                    "............",
                    "........0...",
                    ".....0......",
                    ".......0....",
                    "....0.......",
                    "......A.....",
                    "............",
                    "............",
                    "........A...",
                    ".........A..",
                    "............",
                    "............",
                )
            )
        )
    }

}