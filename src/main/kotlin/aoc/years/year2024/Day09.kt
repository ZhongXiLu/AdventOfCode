package aoc.years.year2024

import aoc.Day
import java.math.BigInteger

@Year2024
class Day09 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return getDisk(input).reorder().getChecksum()
    }

    override fun solvePart2(input: List<String>): Any {
        return getDisk(input).reorderWithoutFragmentation().getChecksum()
    }

    private fun getDisk(input: List<String>): MutableList<String> {
        return input.first().map { it }
            .mapIndexed { index, length ->
                List(length.digitToInt()) { if (index % 2 == 1) "." else (index / 2).toString() }
            }
            .flatten()
            .toMutableList()
    }

}

private fun MutableList<String>.reorder(): List<String> {
    for ((index, block) in this.withIndex()) {
        if (block == ".") {
            val blockToMove = this.withIndex().reversed().first { it.value != "." }.index
            if (blockToMove < index) {
                break
            }
            this[index] = this[blockToMove]
            this[blockToMove] = "."
        }
    }

    return this
}

private fun MutableList<String>.reorderWithoutFragmentation(): List<String> {
    for (index in this.indices.reversed()) {
        val block = this[index]
        if (block != ".") {
            val blockSize = this.filter { it == block }.size
            val emptyBlocks = this.withIndex().windowed(blockSize).firstOrNull { b -> b.all { it.value == "." } }?.map { it.index }

            if (emptyBlocks != null) {
                if (emptyBlocks.first() > index) {
                    continue
                }
                emptyBlocks.forEachIndexed { offset, emptyBlock ->
                    this[emptyBlock] = block
                    this[index - offset] = "."
                }
            }
        }
    }

    return this
}

private fun List<String>.getChecksum(): BigInteger {
    return this.mapIndexed { index, id ->
        if (id != ".") index.toBigInteger() * id.toBigInteger() else BigInteger.ZERO
    }.reduce(BigInteger::plus)
}
