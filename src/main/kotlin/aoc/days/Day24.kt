package aoc.days

import aoc.Day

class Day24 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return Valley.of(input).findStepsToEnd()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
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

    fun findStepsToEnd(): Int {
        val startPosition = getStartingPosition()
        val endPosition = getEndPosition()
        val queue = mutableListOf(Triple(startPosition, map, 0))

        while (queue.isNotEmpty()) {
            val (currentPos, curValley, steps) = queue.removeFirst()
            if (currentPos == endPosition) return steps

            moveBlizzards(curValley)

            findValidPlayerPositions(curValley, currentPos)
                .forEach {
                    queue.add(Triple(it, curValley.deepCopy(), steps + 1))
                }
        }

        return 0
    }

    private fun moveBlizzards(valley: MutableList<MutableList<MutableList<Char>>>) {
        valley.mapIndexed { x, row ->
            row.mapIndexed { y, blizzards ->
                blizzards.mapNotNull { blizzard ->
                    if (blizzard in BLIZZARD_MOVEMENT) return@mapNotNull blizzard.moveFrom(Pair(x, y)) else null
                }
            }.flatten()
        }
            .flatten()
            .forEach { it.move(valley) }
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

    private fun BlizzardMovement.move(valley: MutableList<MutableList<MutableList<Char>>>) {
        valley[this.from.first][this.from.second].remove(this.char)
        valley[this.to.first][this.to.second].add(this.char)
    }

    private fun findValidPlayerPositions(
        valley: MutableList<MutableList<MutableList<Char>>>,
        pos: Pair<Int, Int>
    ): List<Pair<Int, Int>> {
        val (x, y) = pos
        return listOf(pos, Pair(x - 1, y), Pair(x + 1, y), Pair(x, y - 1), Pair(x, y + 1))
            .filter { it.first in valley.indices && it.second in valley.first().indices }
            .filter { valley[it.first][it.second].isEmpty() }
    }

    private fun getStartingPosition(): Pair<Int, Int> {
        return Pair(0, map.first().indexOfFirst { it.isEmpty() })
    }

    private fun getEndPosition(): Pair<Int, Int> {
        return Pair(map.size - 1, map.last().indexOfFirst { it.isEmpty() })
    }

    override fun toString(): String {
        return map.joinToString("\n") { it.joinToString("") }
    }

}

private data class BlizzardMovement(val char: Char, val from: Pair<Int, Int>, val to: Pair<Int, Int>)

private fun MutableList<MutableList<MutableList<Char>>>.deepCopy(): MutableList<MutableList<MutableList<Char>>> {
    return this.map { row -> row.map { chars -> chars.map { it }.toMutableList() }.toMutableList() }.toMutableList()
}
