package aoc.days

import aoc.Day

class Day23 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val grove = Grove.of(input, 10)

        grove.simulate(10)

        return grove.getEmptyTilesInElfSquare()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private class Grove(val grove: MutableList<MutableList<Char>>) {
    val directions = mutableListOf(NORTH_SQUARES, SOUTH_SQUARES, WEST_SQUARES, EAST_SQUARES)

    companion object {
        var NORTH_SQUARES = setOf(Pair(-1, 0), Pair(-1, -1), Pair(-1, 1))
        var SOUTH_SQUARES = setOf(Pair(1, 0), Pair(1, -1), Pair(1, 1))
        var WEST_SQUARES = setOf(Pair(0, -1), Pair(-1, -1), Pair(1, -1))
        var EAST_SQUARES = setOf(Pair(0, 1), Pair(-1, 1), Pair(1, 1))
        var ADJ_SQUARES = NORTH_SQUARES + SOUTH_SQUARES + WEST_SQUARES + EAST_SQUARES

        fun of(input: List<String>, rounds: Int): Grove {
            // Make the grove big enough to run the simulation
            val grove = input.map {
                (0..rounds).map { '.' }.toMutableList()
                    .plus(it.toMutableList())
                    .plus((0..rounds).map { '.' }).toMutableList()
            }
            val expandedGrove = (0..rounds).map {
                (0 until grove.first().size).map { '.' }.toMutableList()
            }
                .plus(grove.toMutableList())
                .plus((0..rounds).map {
                    (0 until grove.first().size).map { '.' }.toMutableList()
                }).toMutableList()
            return Grove(expandedGrove)
        }
    }

    fun simulate(rounds: Int) {
        repeat(rounds) {
            val proposals = getElves()
                .filter { it.surroundedByOtherElf() }
                .mapNotNull { it.proposeStep() }
            val approvedProposals = proposals
                .filter { p1 -> proposals.filter { p2 -> p1.from != p2.from }.none { p2 -> p1.to == p2.to } }
            approvedProposals
                .forEach { this.executeProposal(it) }

            directions.add(directions.removeFirst())
        }
    }

    private fun getElves(): List<Pair<Int, Int>> {
        return grove.mapIndexed { x, row ->
            row.mapIndexed { y, char ->
                if (char == '#') Pair(x, y) else null
            }.filterNotNull()
        }.flatten()
    }

    private fun Pair<Int, Int>.surroundedByOtherElf(): Boolean {
        return ADJ_SQUARES.any { adjSquare -> grove[this.first + adjSquare.first][this.second + adjSquare.second] == '#' }
    }

    private fun Pair<Int, Int>.proposeStep(): Proposal? {
        val direction = directions
            .firstOrNull { it.all { adjSquare -> grove[this.first + adjSquare.first][this.second + adjSquare.second] == '.' } }
        return if (direction != null) {
            Proposal(this, Pair(this.first + direction.first().first, this.second + direction.first().second))
        } else {
            null
        }
    }

    private fun executeProposal(proposal: Proposal) {
        grove[proposal.from.first][proposal.from.second] = '.'
        grove[proposal.to.first][proposal.to.second] = '#'
    }

    fun getEmptyTilesInElfSquare(): Int {
        val (xBoundary, yBoundary) = this.getElfSquare()
        return (xBoundary.first..xBoundary.second).sumOf { x ->
            (yBoundary.first..yBoundary.second).count { y ->
                grove[x][y] == '.'
            }
        }
    }

    private fun getElfSquare(): Pair<Pair<Int, Int>, Pair<Int, Int>> {
        val minX = grove.takeWhile { !it.contains('#') }.count()
        val maxX = grove.size - 1 - grove.takeLastWhile { !it.contains('#') }.count()
        val minY = grove.map { row -> row.indexOfFirst { it == '#' } }.filter { it != -1 }.min()
        val maxY = grove.maxOf { row -> row.indexOfLast { it == '#' } }
        return Pair(Pair(minX, maxX), Pair(minY, maxY))
    }

    override fun toString(): String {
        return grove.joinToString("\n") { it.joinToString("") }
    }

}

private data class Proposal(val from: Pair<Int, Int>, val to: Pair<Int, Int>)
