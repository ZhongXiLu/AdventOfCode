package aoc.years.year2022

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day17Test {

    private val day = Day17()
    private val testInput = listOf(
        ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"
    )

    @Test
    fun testPart1() {
        assertEquals(3068L, day.solvePart1(testInput))
    }

    @Test
    fun testPart2() {
        assertEquals(1514285714288L, day.solvePart2(testInput))
    }

}