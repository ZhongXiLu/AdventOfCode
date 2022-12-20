package aoc.days

import aoc.Day
import java.util.*

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
            if (value.second != 0) {
                val oldIndex = uniqueNumbers.indexOf(value)
                val newIndex = getIndex(oldIndex + value.second + if (value.second > 0) 1 else 0)
                moveNumber(value, oldIndex, newIndex)
            }
        }
    }

    fun getGroveCoords(): Int {
        val indexOfZero = uniqueNumbers.indexOfFirst { it.second == 0 }
        return this[indexOfZero + 1000] + this[indexOfZero + 2000] + this[indexOfZero + 3000]
    }

    operator fun get(i: Int): Int {
        return uniqueNumbers[getIndex(i)].second
    }

    private fun moveNumber(value: Pair<Int, Int>, oldIndex: Int, newIndex: Int) {
        uniqueNumbers.add(newIndex, value)
        uniqueNumbers.removeAt(oldIndex + if (newIndex > oldIndex) 0 else 1)
    }

    private fun getIndex(i: Int): Int {
        var newIndex = i % numbers.size
        if (newIndex <= 0) newIndex += numbers.size
        return newIndex
    }

}
