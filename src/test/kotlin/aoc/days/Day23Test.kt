package aoc.days

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day23Test {

    private val day = Day23()
    private val testInput = listOf(
        "....#..",
        "..###.#",
        "#...#.#",
        ".#...##",
        "#.###..",
        "##.#.##",
        ".#..#.."
    )

    @Test
    fun testPart1() {
        assertEquals(110, day.solvePart1(testInput))
    }

}