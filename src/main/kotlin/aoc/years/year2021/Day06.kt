package aoc.years.year2021

import aoc.Day
import java.math.BigInteger

@Year2021
class Day06 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return generateSequence(input.toLanternFish()) { it.cycle() }
            .take(80)
            .last()
            .values.sumOf { it }
    }

    override fun solvePart2(input: List<String>): Any {
        return generateSequence(input.toLanternFish()) { it.cycle() }
            .take(256)
            .last()
            .values.sumOf { it }
    }

    private fun List<String>.toLanternFish(): Map<Int, BigInteger> {
        return this.first()
            .split(",")
            .map { it.toInt() }
            .groupBy { it }
            .mapValues { it.value.size.toBigInteger() }
    }

    private fun Map<Int, BigInteger>.cycle(): Map<Int, BigInteger> {
        return this.entries
            .groupBy { if (it.key == 0) 6 else it.key - 1 }
            .mapValues { entry -> entry.value.sumOf { it.value } }
            .plus(8 to this.getOrDefault(0, BigInteger.ZERO))
    }
}
