package aoc.days

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day17Test {

    private val day = Day17()
    private val testInput = listOf(
        ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"
    )

    @Test
    fun testPart1() {
        assertEquals(3068, day.solvePart1(testInput))
    }

}