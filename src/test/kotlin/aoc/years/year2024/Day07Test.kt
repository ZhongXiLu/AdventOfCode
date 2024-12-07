package aoc.years.year2024

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day07Test {

    @Test
    fun testPart1() {
        val day = Day07()
        assertEquals(
            BigInteger.valueOf(3749), day.solvePart1(
                listOf(
                    "190: 10 19",
                    "3267: 81 40 27",
                    "83: 17 5",
                    "156: 15 6",
                    "7290: 6 8 6 15",
                    "161011: 16 10 13",
                    "192: 17 8 14",
                    "21037: 9 7 18 13",
                    "292: 11 6 16 20",
                )
            )
        )
    }

    @Test
    fun testPart2() {
        val day = Day07()
        assertEquals(
            BigInteger.valueOf(11387), day.solvePart2(
                listOf(
                    "190: 10 19",
                    "3267: 81 40 27",
                    "83: 17 5",
                    "156: 15 6",
                    "7290: 6 8 6 15",
                    "161011: 16 10 13",
                    "192: 17 8 14",
                    "21037: 9 7 18 13",
                    "292: 11 6 16 20",
                )
            )
        )
    }

}