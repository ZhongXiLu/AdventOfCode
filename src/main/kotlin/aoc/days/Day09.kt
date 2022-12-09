package aoc.days

import aoc.Day
import kotlin.math.abs

class Day09 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val bridge = Bridge()

        input
            .map { it.split(" ") }
            .forEach { (direction, steps) -> bridge.moveHead(direction, steps.toInt()) }

        return bridge.tailPositions.size
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private class Bridge {
    var head = Pair(0, 0)
    var tail = Pair(0, 0)
    var tailPositions = mutableSetOf(tail)

    fun moveHead(direction: String, steps: Int) {
        repeat(steps) {
            moveHeadOneStep(direction)
            adjustTail()
            tailPositions.add(tail)
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

    private fun adjustTail() {
        if (tailNotTouchingHead()) {
            if (tail.first == head.first) {
                // Same row
                tail = Pair(tail.first, tail.second + if (tail.second < head.second) 1 else -1)

            } else if (tail.second == head.second) {
                // Same column
                tail = Pair(tail.first + if (tail.first < head.first) 1 else -1, tail.second)

            } else {
                // Move diagonally
                tail = Pair(
                    tail.first + if (tail.first < head.first) 1 else -1,
                    tail.second + if (tail.second < head.second) 1 else -1
                )
            }
        }
    }

    private fun tailNotTouchingHead(): Boolean {
        return abs(tail.first.minus(head.first)) > 1 || abs(tail.second.minus(head.second)) > 1
    }
}
