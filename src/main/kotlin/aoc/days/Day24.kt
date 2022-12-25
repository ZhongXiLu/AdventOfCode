package aoc.days

import aoc.Day

class Day24 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val valley = Valley.of(input)
        return valley.timeFromSourceToTarget(valley.startingPosition(), valley.endPosition())
    }

    override fun solvePart2(input: List<String>): Any {
        val valley = Valley.of(input)
        return valley.timeFromSourceToTarget(valley.startingPosition(), valley.endPosition())
            .plus(valley.timeFromSourceToTarget(valley.endPosition(), valley.startingPosition()))
            .plus(valley.timeFromSourceToTarget(valley.startingPosition(), valley.endPosition()))
    }

}

// I should stop creating these God classes...
private class Valley(val map: MutableList<MutableList<MutableList<Char>>>) {

    companion object {
        val BLIZZARD_MOVEMENT = mapOf(
            '^' to Pair(-1, 0),
            '>' to Pair(0, 1),
            'v' to Pair(1, 0),
            '<' to Pair(0, -1)
        )

        fun of(input: List<String>): Valley {
            return Valley(input.map { row ->
                row.toCharArray().map { if (it == '.') mutableListOf() else mutableListOf(it) }.toMutableList()
            }.toMutableList())
        }
    }

    fun timeFromSourceToTarget(source: Pair<Int, Int>, target: Pair<Int, Int>): Int {
        var currentBatch: MutableSet<Pair<Pair<Int, Int>, Int>>
        val nextBatch = mutableSetOf(Pair(source, 0))

        while (nextBatch.isNotEmpty()) {
            currentBatch = nextBatch.map { it }.toMutableSet()
            nextBatch.clear()

            val atTarget = currentBatch.firstOrNull { return@firstOrNull it.first == target }
            if (atTarget != null) return atTarget.second

            moveBlizzards()

            currentBatch.forEach { state ->
                val (currentPos, steps) = state
                if (currentPos == target) return steps

                findValidPlayerPositions(currentPos).forEach {
                    nextBatch.add(Pair(it, steps + 1))
                }
            }
        }

        return 0
    }

    fun startingPosition(): Pair<Int, Int> {
        return Pair(0, map.first().indexOfFirst { it.isEmpty() })
    }

    fun endPosition(): Pair<Int, Int> {
        return Pair(map.size - 1, map.last().indexOfFirst { it.isEmpty() })
    }

    private fun moveBlizzards() {
        map.mapIndexed { x, row ->
            row.mapIndexed { y, blizzards ->
                blizzards.mapNotNull { blizzard ->
                    if (blizzard in BLIZZARD_MOVEMENT) return@mapNotNull blizzard.moveFrom(Pair(x, y)) else null
                }
            }.flatten()
        }
            .flatten()
            .forEach { it.move() }
    }

    private fun Char.moveFrom(oldPos: Pair<Int, Int>): BlizzardMovement {
        val (x, y) = BLIZZARD_MOVEMENT[this]!!
        var (newX, newY) = Pair(oldPos.first + x, oldPos.second + y)
        if (map[newX][newY].contains('#')) {
            when (this) {
                '^' -> newX = map.size - 2
                'v' -> newX = 1
                '>' -> newY = 1
                '<' -> newY = map.first().size - 2
            }
        }

        return BlizzardMovement(this, oldPos, Pair(newX, newY))
    }

    private fun BlizzardMovement.move() {
        map[this.from.first][this.from.second].remove(this.char)
        map[this.to.first][this.to.second].add(this.char)
    }

    private fun findValidPlayerPositions(pos: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (x, y) = pos
        return listOf(pos, Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
            .filter { it.first in map.indices && it.second in map.first().indices }
            .filter { map[it.first][it.second].isEmpty() }
    }

    override fun toString(): String {
        return map.joinToString("\n") { it.joinToString("") }
    }

}

private data class BlizzardMovement(val char: Char, val from: Pair<Int, Int>, val to: Pair<Int, Int>)

