package aoc.years.year2024

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day04Test {

    @Test
    fun testPart1() {
        val day = Day04()
        assertEquals(
            18, day.solvePart1(
                listOf(
                    "....XXMAS.",
                    ".SAMXMS...",
                    "...S..A...",
                    "..A.A.MS.X",
                    "XMASAMX.MM",
                    "X.....XA.A",
                    "S.S.S.S.SS",
                    ".A.A.A.A.A",
                    "..M.M.M.MM",
                    ".X.X.XMASX",
                )
            )
        )
    }

}