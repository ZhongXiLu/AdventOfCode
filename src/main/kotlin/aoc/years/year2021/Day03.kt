package aoc.years.year2021

import aoc.Day

@Year2021
class Day03 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val gamma = input.first().indices
            .fold("") { gamma, index -> gamma.plus(input.mostCommonBitAt(index)) }
            .toInt(2)
        val epsilon = input.first().indices
            .fold("") { epsilon, index -> epsilon.plus(input.leastCommonBitAt(index)) }
            .toInt(2)

        return gamma * epsilon
    }

    override fun solvePart2(input: List<String>): Any {
        val oxygenGeneratorRating = input.oxygenGeneratorRating()
        val co2ScrubberRating = input.co2ScrubberRating()

        return oxygenGeneratorRating * co2ScrubberRating
    }

}

private fun List<String>.mostCommonBitAt(index: Int): Char {
    val (nrOfZeroBits, nrOfOneBits) = countBits(index)
    return if (nrOfOneBits >= nrOfZeroBits) '1' else '0'
}

private fun List<String>.leastCommonBitAt(index: Int): Char {
    val (nrOfZeroBits, nrOfOneBits) = countBits(index)
    return if (nrOfOneBits >= nrOfZeroBits) '0' else '1'
}

private fun List<String>.countBits(index: Int): Pair<Int, Int> {
    val nrOfZeroBits = this.count { it.elementAt(index) == '0' }
    val nrOfOneBits = this.count { it.elementAt(index) == '1' }
    return Pair(nrOfZeroBits, nrOfOneBits)
}

private fun List<String>.oxygenGeneratorRating(): Int {
    return this.getRating(List<String>::mostCommonBitAt)
}

private fun List<String>.co2ScrubberRating(): Int {
    return this.getRating(List<String>::leastCommonBitAt)
}

private fun List<String>.getRating(findBitFunction: List<String>.(Int) -> Char): Int {
    var currentList = this
    currentList.first().indices
        .forEach {index ->
            if (currentList.size > 1) {
                val bitToMatch = currentList.findBitFunction(index)
                currentList = currentList.filter {
                    it.elementAt(index) == bitToMatch
                }
            }
        }
    return currentList.first().toInt(2)
}
