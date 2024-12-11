package aoc.years.year2024

import aoc.Day
import java.math.BigInteger

@Year2024
class Day11 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val memoization = HashMap<BigInteger, BigInteger>()
        return getStones(input).sumOf { it.getNrOfStonesAfter(25, memoization) }
    }

    override fun solvePart2(input: List<String>): Any {
        // try recursive with memoization?
        val memoization = HashMap<BigInteger, BigInteger>()
        return getStones(input).sumOf { it.getNrOfStonesAfter(25, memoization) }
    }

    private fun getStones(input: List<String>) = input.first().split(" ").map { it.toBigInteger() }
}

private fun BigInteger.getNrOfStonesAfter(blinks: Int, memoization: MutableMap<BigInteger, BigInteger>): Int {
    if (blinks == 0) {
        return 1
    }

    if (this == BigInteger.ZERO) {
        val nrOfStones = BigInteger.ONE.getNrOfStonesAfter(blinks - 1, memoization)
        //
        return nrOfStones
    } else {
        val digit = this.toString()
        if (digit.length % 2 == 0) {
            val firstStone = digit.substring(0, digit.length / 2).toBigInteger()
            val secondStone = digit.substring(digit.length / 2).toBigInteger()
            return firstStone.getNrOfStonesAfter(blinks - 1, memoization) + secondStone.getNrOfStonesAfter(blinks - 1, memoization)
        } else {
            return (this * BigInteger.valueOf(2024)).getNrOfStonesAfter(blinks - 1, memoization)
        }
    }
}

private fun List<BigInteger>.blink(): List<BigInteger> {
    return this.map {
        val digit = it.toString()
        if (it == BigInteger.ZERO) {
            return@map listOf(BigInteger.ONE)
        } else {
            if (digit.length % 2 == 0) {
                val firstStone = digit.substring(0, digit.length / 2).toBigInteger()
                val secondStone = digit.substring(digit.length / 2).toBigInteger()
                return@map listOf(firstStone, secondStone)
            } else {
                return@map listOf(it * BigInteger.valueOf(2024))
            }
        }
    }.flatten()
}
