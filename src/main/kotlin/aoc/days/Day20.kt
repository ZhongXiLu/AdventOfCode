package aoc.days

import aoc.Day
import java.util.*
import kotlin.math.max
import kotlin.math.min

class Day20 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val encryptedFile = EncryptedFile.of(input)

        encryptedFile.mix()

        return encryptedFile.getGroveCoords()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private class EncryptedFile(val numbers: LinkedList<Int>) {
    val uniqueNumbers = LinkedList(numbers.mapIndexed { index, i -> Pair(index, i) })

    companion object {
        fun of(input: List<String>): EncryptedFile {
            return EncryptedFile(LinkedList(input.map { it.toInt() }))
        }
    }

    fun mix() {
        uniqueNumbers.toList().forEach { value ->
            val oldIndex = uniqueNumbers.indexOf(value)
            val newIndex = getNewIndex(oldIndex + (value.second))
            moveNumber(oldIndex, newIndex)
        }
    }

    fun getGroveCoords(): Int {
        val indexOfZero = uniqueNumbers.indexOfFirst { it.second == 0 }
        return this[indexOfZero + 1000] + this[indexOfZero + 2000] + this[indexOfZero + 3000]
    }

    operator fun get(i: Int): Int {
        return uniqueNumbers[i % uniqueNumbers.size].second
    }

    private fun moveNumber(oldIndex: Int, newIndex: Int) {
        Collections.rotate(
            uniqueNumbers.subList(min(oldIndex, newIndex), max(oldIndex, newIndex) + 1),
            if (newIndex > oldIndex) -1 else 1
        )
    }

    private fun getNewIndex(i: Int): Int {
        var newIndex = i % (numbers.size - 1)
        if (newIndex <= 0) newIndex += (numbers.size - 1)
        return newIndex
    }

}
