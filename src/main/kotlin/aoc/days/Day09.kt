package aoc.days

import aoc.Day
import kotlin.math.abs

class Day09 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val bridge = Bridge()

        input
            .map { it.split(" ") }
            .forEach { (direction, steps) -> bridge.moveHead(direction, steps.toInt()) }

        return bridge.ropeEndPositions.size
    }

    override fun solvePart2(input: List<String>): Any {
        val bridge = Bridge()
        repeat(8) { bridge.rope.add(Pair(0, 0)) }

        input
            .map { it.split(" ") }
            .forEach { (direction, steps) -> bridge.moveHead(direction, steps.toInt()) }

        return bridge.ropeEndPositions.size
    }

}

private class Bridge {
    var rope = mutableListOf(Pair(0, 0), Pair(0, 0))
    var ropeEndPositions = mutableSetOf(rope.last())

    fun moveHead(direction: String, steps: Int) {
        repeat(steps) {
            moveHead(direction)

            (1 until rope.size)
                .forEach { rope[it] = moveTail(rope[it], rope[it - 1]) }

            ropeEndPositions.add(rope.last())
        }
    }

    private fun moveHead(direction: String) {
        rope[0] = when (direction) {
            "R" -> Pair(rope[0].first, rope[0].second + 1)
            "D" -> Pair(rope[0].first - 1, rope[0].second)
            "L" -> Pair(rope[0].first, rope[0].second - 1)
            "U" -> Pair(rope[0].first + 1, rope[0].second)
            else -> throw IllegalArgumentException("Unknown direction $direction")
        }
    }

    private fun moveTail(tail: Pair<Int, Int>, head: Pair<Int, Int>): Pair<Int, Int> {
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
