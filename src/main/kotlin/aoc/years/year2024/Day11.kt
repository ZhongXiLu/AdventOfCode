package aoc.years.year2024

import aoc.Day
import java.math.BigInteger

@Year2024
class Day11 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val memoization = HashMap<String, BigInteger>()
        return getStones(input).sumOf { it.getNrOfStonesAfter(25, memoization) }
    }

    override fun solvePart2(input: List<String>): Any {
        val memoization = HashMap<String, BigInteger>()
        return getStones(input).sumOf { it.getNrOfStonesAfter(75, memoization) }
    }

    private fun getStones(input: List<String>) = input.first().split(" ").map { it.toBigInteger() }
}

private fun BigInteger.getNrOfStonesAfter(blinks: Int, memoization: MutableMap<String, BigInteger>): BigInteger {
    if (blinks == 0) {
        return BigInteger.ONE
    }

    val memoizationKey = "$this $blinks"
    if (memoization.containsKey(memoizationKey)) {
        return memoization[memoizationKey]!!
    }

    val nrOfStones: BigInteger = getNrOfStones(blinks, memoization)
    memoization[memoizationKey] = nrOfStones

    return nrOfStones
}

private fun BigInteger.getNrOfStones(blinks: Int, memoization: MutableMap<String, BigInteger>): BigInteger {
    val nrOfStones: BigInteger

    if (this == BigInteger.ZERO) {
        nrOfStones = BigInteger.ONE.getNrOfStonesAfter(blinks - 1, memoization)
    } else {
        val digit = this.toString()
        if (digit.length % 2 == 0) {
            val firstStone = digit.substring(0, digit.length / 2).toBigInteger()
            val secondStone = digit.substring(digit.length / 2).toBigInteger()
            nrOfStones = firstStone.getNrOfStonesAfter(blinks - 1, memoization)
                .plus(secondStone.getNrOfStonesAfter(blinks - 1, memoization))
        } else {
            nrOfStones = (this * BigInteger.valueOf(2024)).getNrOfStonesAfter(blinks - 1, memoization)
        }
    }

    return nrOfStones
}
