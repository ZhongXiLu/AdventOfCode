package aoc.years.year2024

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore("Hardcoded the line numbers")
internal class Day15Test {

    @Test
    fun testPart1() {
        val day = Day15()
        assertEquals(
            2028, day.solvePart1(
                listOf(
                    "########",
                    "#..O.O.#",
                    "##@.O..#",
                    "#...O..#",
                    "#.#.O..#",
                    "#...O..#",
                    "#......#",
                    "########",
                    "",
                    "<^^>>>vv<v>>v<<",
                )
            )
        )
    }

    @Test
    fun testPart2() {
        val day = Day15()
        assertEquals(
            9021, day.solvePart2(
                listOf(
                    "##########",
                    "#..O..O.O#",
                    "#......O.#",
                    "#.OO..O.O#",
                    "#..O@..O.#",
                    "#O#..O...#",
                    "#O..O..O.#",
                    "#.OO.O.OO#",
                    "#....O...#",
                    "##########",
                    "",
                    "<vv>^<v^>v>^vv^v>v<>v^v<v<^vv<<<^><<><>>v<vvv<>^v^>^<<<><<v<<<v^vv^v>^",
                    "vvv<<^>^v^^><<>>><>^<<><^vv^^<>vvv<>><^^v>^>vv<>v<<<<v<^v>^<^^>>>^<v<v",
                    "><>vv>v^v^<>><>>>><^^>vv>v<^^^>>v^v^<^^>v^^>v^<^v>v<>>v^v^<v>v^^<^^vv<",
                    "<<v<^>>^^^^>>>v^<>vvv^><v<<<>^^^vv^<vvv>^>v<^^^^v<>^>vvvv><>>v^<<^^^^^",
                    "^><^><>>><>^^<<^^v>>><^<v>^<vv>>v>>>^v><>^v><<<<v>>v<v<v>vvv>^<><<>^><",
                    "^>><>^v<><^vvv<^^<><v<<<<<><^v<<<><<<^^<v<^^^><^>>^<v^><<<^>>^v<v^v<v^",
                    ">^>>^v>vv>^<<^v<>><<><<v<<v><>v<^vv<<<>^^v^>^^>>><<^v>>v^v><^^>>^<>vv^",
                    "<><^^>^^^<><vvvvv^v<v<<>^v<v>v<<^><<><<><<<^^<<<^<<>><<><^^^>^^<>^>v<>",
                    "^^>vv<^v^v<vv>^<><v<^v>^^^>>>^^vvv^>vvv<>>>^<^>>>>>^<<^v>^vvv<>^<><<v>",
                    "v^^>>><<^^<>>^v^<v^vv<>v^<<>^<^v^v><^<<<><<^<v><v<>vv>>v><v^<vv<>v^<<^",
                )
            )
        )
    }


}