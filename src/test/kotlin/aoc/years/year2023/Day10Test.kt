package aoc.years.year2023

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day10Test {

    @Test
    fun testPart1() {
        val day = Day10()
        assertEquals(
            4, day.solvePart1(
                listOf(
                    ".....",
                    ".S-7.",
                    ".|.|.",
                    ".L-J.",
                    ".....",
                )
            )
        )
    }

    @Test
    fun testPart1_2() {
        val day = Day10()
        assertEquals(
            8, day.solvePart1(
                listOf(
                    "7-F7-",
                    ".FJ|7",
                    "SJLL7",
                    "|F--J",
                    "LJ.LJ7-F7-",
                    ".FJ|7",
                    "SJLL7",
                    "|F--J",
                    "LJ.LJ",
                )
            )
        )
    }

    @Test
    fun testPart2() {
        val day = Day10()
        assertEquals(
            4, day.solvePart2(
                listOf(
                    "...........",
                    ".S-------7.",
                    ".|F-----7|.",
                    ".||.....||.",
                    ".||.....||.",
                    ".|L-7.F-J|.",
                    ".|..|.|..|.",
                    ".L--J.L--J.",
                    "...........",
                )
            )
        )
    }

}
