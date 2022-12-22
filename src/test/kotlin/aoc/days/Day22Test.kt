package aoc.days

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
    fun testPart1() {
        assertEquals(6032, day.solvePart1(testInput))
    }

}