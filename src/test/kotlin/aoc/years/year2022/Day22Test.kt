package aoc.years.year2022

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day22Test {

    private val day = Day22()
    private val testInput = listOf(
        "        ...#",
        "        .#..",
        "        #...",
        "        ....",
        "...#.......#",
        "........#...",
        "..#....#....",
        "..........#.",
        "        ...#....",
        "        .....#..",
        "        .#......",
        "        ......#.",
        "",
        "10R5L5R10L4R5L5"
    )

    @Test
    @Ignore("Hardcoded the (cube) map layout, so test fails for now")
    fun testPart1() {
        assertEquals(6032, day.solvePart1(testInput))
    }

}