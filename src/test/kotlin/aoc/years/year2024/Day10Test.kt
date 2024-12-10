package aoc.years.year2024

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day10Test {

    @Test
    fun testPart1_1() {
        val day = Day10()
        assertEquals(
            4, day.solvePart1(
                listOf(
                    "9990999",
                    "9991999",
                    "9992999",
                    "6543456",
                    "7999997",
                    "8999998",
                    "9999999",
                )
            )
        )
    }

    @Test
    fun testPart1_2() {
        val day = Day10()
        assertEquals(
            5, day.solvePart1(
                listOf(
                    "9990999",
                    "9991998",
                    "9992997",
                    "6543456",
                    "7659987",
                    "8769999",
                    "9879999",
                )
            )
        )
    }

    @Test
    fun testPart1() {
        val day = Day10()
        assertEquals(
            36, day.solvePart1(
                listOf(
                    "89010123",
                    "78121874",
                    "87430965",
                    "96549874",
                    "45678903",
                    "32019012",
                    "01329801",
                    "10456732",
                )
            )
        )
    }

}