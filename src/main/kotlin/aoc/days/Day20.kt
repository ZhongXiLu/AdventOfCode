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
    val numberIndices = numbers.mapIndexed { index, i -> Pair(i, index) }.toMap().toMutableMap()

    companion object {
        fun of(input: List<String>): EncryptedFile {
            return EncryptedFile(LinkedList(input.map { it.toInt() }))
        }
    }

    fun mix() {
        numbers.toList().forEach { value ->
            if (value != 0) {
                val oldIndex = numberIndices[value]!!
                var newIndex = getIndex(oldIndex + value + if (value > 0) 1 else 0)
                if (newIndex == 0) newIndex = numbers.size
                moveNumber(value, oldIndex, newIndex)
                shiftNumbers(oldIndex, newIndex)
            }
        }
    }

    fun getGroveCoords(): Int {
        val indexOfZero = numbers.indexOf(0)
        return this[indexOfZero + 1000] + this[indexOfZero + 2000] + this[indexOfZero + 3000]
    }

    operator fun get(i: Int): Int {
        return numbers[getIndex(i)]
    }

    private fun shiftNumbers(oldIndex: Int, newIndex: Int) {
        numbers.mapIndexed { index, i -> Pair(index, i) }
            .filter { (index, _) -> index in (oldIndex until newIndex) || index in (oldIndex downTo newIndex + 1) }
            .forEach { (_, value) ->
                numberIndices[value] = numberIndices[value]!! + if (newIndex > oldIndex) -1 else 1
            }
    }

    private fun moveNumber(value: Int, oldIndex: Int, newIndex: Int) {
        numbers.add(newIndex, value)
        if (newIndex > oldIndex || newIndex == 0) {
            numbers.removeAt(oldIndex)
            numberIndices[value] = numberIndices[value]!! + newIndex - oldIndex
        } else {
            numbers.removeAt(oldIndex + 1)
            numberIndices[value] = numberIndices[value]!! - oldIndex - newIndex
        }
    }

    private fun getIndex(i: Int): Int {
        var newIndex = i % numbers.size
        if (newIndex < 0) newIndex += numbers.size
        return newIndex
    }

}
