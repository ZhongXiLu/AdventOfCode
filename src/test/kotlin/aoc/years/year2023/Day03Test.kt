package aoc.years.year2023

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day03Test {

    @Test
    fun testPart1() {
        val day = Day03()
        assertEquals(
            4361, day.solvePart1(
                listOf(
                    "467..114..",
                    "...*......",
                    "..35..633.",
                    "......#...",
                    "617*......",
                    ".....+.58.",
                    "..592.....",
                    "......755.",
                    "...$.*....",
                    ".664.598.."
                )
            )
        )
    }

    @Test
    fun testPart1_duplicatePartNumbers() {
        val day = Day03()
        assertEquals(
            4008, day.solvePart1(
                listOf(
                    "114..114..",
                    "...*......",
                    "..35..633.",
                    "......#...",
                    "617*......",
                    ".....+.58.",
                    "..592.....",
                    "......755.",
                    "...$.*....",
                    ".664.598.."
                )
            )
        )
    }

}