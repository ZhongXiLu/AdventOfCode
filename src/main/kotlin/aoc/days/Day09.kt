package aoc.days

import aoc.Day
import kotlin.math.abs

class Day09 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val bridge = Bridge()

        input
            .map { it.split(" ") }
            .forEach { (direction, steps) -> bridge.moveHead(direction, steps.toInt()) }

        return bridge.tailEndPositions.size
    }

    override fun solvePart2(input: List<String>): Any {
        val bridge = Bridge()
        repeat(8) { bridge.tails.add(Pair(0, 0)) }

        input
            .map { it.split(" ") }
            .forEach { (direction, steps) -> bridge.moveHead(direction, steps.toInt()) }

        return bridge.tailEndPositions.size
    }

}

private class Bridge {
    var head = Pair(0, 0)
    var tails = mutableListOf(Pair(0, 0))
    var tailEndPositions = mutableSetOf(tails.last())

    fun moveHead(direction: String, steps: Int) {
        repeat(steps) {
            moveHeadOneStep(direction)
            tails[0] = adjustTailTo(tails[0], head)

            // Move other tail parts
            (1 until tails.size)
                .forEach { tails[it] = adjustTailTo(tails[it], tails[it - 1]) }

            tailEndPositions.add(tails.last())
        }
    }

    private fun moveHeadOneStep(direction: String) {
        head = when (direction) {
            "R" -> Pair(head.first, head.second + 1)
            "D" -> Pair(head.first - 1, head.second)
            "L" -> Pair(head.first, head.second - 1)
            "U" -> Pair(head.first + 1, head.second)
            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
    }

    private fun adjustTailTo(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {
        if (tailNotTouchingHead(tail, head)) {
            return if (tail.first == head.first) {
                // Same row
                Pair(tail.first, tail.second + if (tail.second < head.second) 1 else -1)

            } else if (tail.second == head.second) {
                // Same column
                Pair(tail.first + if (tail.first < head.first) 1 else -1, tail.second)

            } else {
                // Move diagonally
                Pair(
                    tail.first + if (tail.first < head.first) 1 else -1,
                    tail.second + if (tail.second < head.second) 1 else -1
                )
            }
        }
        return tail
    }

    private fun tailNotTouchingHead(tail: Pair<Int, Int>, head: Pair<Int, Int>): Boolean {
        return abs(tail.first.minus(head.first)) > 1 || abs(tail.second.minus(head.second)) > 1
    }
}
