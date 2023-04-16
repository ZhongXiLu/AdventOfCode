package aoc.years.year2022

import aoc.Day
import java.util.*
import kotlin.math.max
import kotlin.math.min

@Year2022
class Day20 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val encryptedFile = EncryptedFile.of(input)

        encryptedFile.mix()

        return encryptedFile.getGroveCoords()
    }

    override fun solvePart2(input: List<String>): Any {
        val encryptedFile = EncryptedFile.of(input)

        encryptedFile.decrypt(811589153)
        encryptedFile.mix(10)

        return encryptedFile.getGroveCoords()
    }

}

private class EncryptedFile(val numbers: LinkedList<Long>) {
    var uniqueNumbers = numbers.mapIndexed { index, i -> Pair(index, i) }

    companion object {
        fun of(input: List<String>): EncryptedFile {
            return EncryptedFile(LinkedList(input.map { it.toLong() }))
        }
    }

    fun mix(times: Int = 1) {
        val originalList = uniqueNumbers.toList()
        repeat(times) {
            originalList.forEach { value ->
                val oldIndex = uniqueNumbers.indexOf(value)
                val newIndex = getNewIndex(oldIndex.toLong() + value.second)
                moveNumber(oldIndex, newIndex)
            }
        }
    }

    fun getGroveCoords(): Long {
        val indexOfZero = uniqueNumbers.indexOfFirst { it.second == 0L }
        return this[indexOfZero + 1000] + this[indexOfZero + 2000] + this[indexOfZero + 3000]
    }

    fun decrypt(key: Long) {
        uniqueNumbers = uniqueNumbers.map { (index, value) -> Pair(index, value * key) }
    }

    operator fun get(i: Int): Long {
        return uniqueNumbers[i % uniqueNumbers.size].second
    }

    private fun moveNumber(oldIndex: Int, newIndex: Int) {
        Collections.rotate(
            uniqueNumbers.subList(min(oldIndex, newIndex), max(oldIndex, newIndex) + 1),
            if (newIndex > oldIndex) -1 else 1
        )
    }

    private fun getNewIndex(i: Long): Int {
        var newIndex = i % (numbers.size - 1)
        if (newIndex <= 0) newIndex += (numbers.size - 1)
        return newIndex.toInt()
    }

}
