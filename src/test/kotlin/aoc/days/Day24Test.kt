package aoc.days

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day24Test {

    private val day = Day24()
    private val testInput = listOf(
        "#.######",
        "#>>.<^<#",
        "#.<..<<#",
        "#>v.><>#",
        "#<^v^^>#",
        "######.#"
    )

    @Test
    fun testPart1() {
        assertEquals(18, day.solvePart1(testInput))
    }

}