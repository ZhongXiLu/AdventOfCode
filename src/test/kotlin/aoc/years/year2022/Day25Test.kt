package aoc.years.year2022

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day25Test {

    private val day = Day25()
    private val testInput = listOf(
        "1=-0-2",
        "12111",
        "2=0=",
        "21",
        "2=01",
        "111",
        "20012",
        "112",
        "1=-1=",
        "1-12",
        "12",
        "1=",
        "122"
    )

    @Test
    fun testPart1() {
        assertEquals("2=-1=0", day.solvePart1(testInput))
    }

}