package aoc.years.year2024

import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day09Test {

    @Test
    fun testPart1() {
        val day = Day09()
        assertEquals(
            BigInteger.valueOf(1928), day.solvePart1(
                listOf(
                    "2333133121414131402"
                )
            )
        )
    }

    @Test
    fun testPart2() {
        val day = Day09()
        assertEquals(
            BigInteger.valueOf(2858), day.solvePart2(
                listOf(
                    "2333133121414131402"
                )
            )
        )
    }

}