package aoc.days

import aoc.Day

private const val ROCK_OPP = "A"
private const val PAPER_OPP = "B"
private const val SCISSOR_OPP = "C"
private const val ROCK_SELF = "X"
private const val PAPER_SELF = "Y"
private const val SCISSOR_SELF = "Z"
private const val LOSE = "X"
private const val DRAW = "Y"
private const val WIN = "Z"

class Day02 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .map { it.split(" ") }
            .map { calculateScoreOutcomeOfRound(it[0], it[1]) + calculateScoreShapeSelected(it[1]) }
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .map { it.split(" ") }
            .map { listOf(it[0], chooseOwnShape(it[0], it[1])) }
            .map { calculateScoreOutcomeOfRound(it[0], it[1]) + calculateScoreShapeSelected(it[1]) }
            .sum()
    }

    private fun calculateScoreOutcomeOfRound(opponentShape: String, ownShape: String): Int {
        return when (opponentShape) {
            ROCK_OPP -> {
                when (ownShape) {
                    ROCK_SELF -> 3
                    PAPER_SELF -> 6
                    SCISSOR_SELF -> 0
                    else -> throw Exception("Unknown shape: $ownShape")
                }
            }
            PAPER_OPP -> {
                when (ownShape) {
                    ROCK_SELF -> 0
                    PAPER_SELF -> 3
                    SCISSOR_SELF -> 6
                    else -> throw Exception("Unknown shape: $ownShape")
                }
            }
            SCISSOR_OPP -> {
                when (ownShape) {
                    ROCK_SELF -> 6
                    PAPER_SELF -> 0
                    SCISSOR_SELF -> 3
                    else -> throw Exception("Unknown shape: $ownShape")
                }
            }
            else -> throw Exception("Unknown shape: $opponentShape")
        }
    }

    private fun calculateScoreShapeSelected(shape: String): Int {
        return when (shape) {
            ROCK_SELF -> 1
            PAPER_SELF -> 2
            SCISSOR_SELF -> 3
            else -> throw Exception("Unknown shape: $shape")
        }
    }

    private fun chooseOwnShape(opponentShape: String, ownShape: String): String {
        return when (opponentShape) {
            ROCK_OPP -> {
                when (ownShape) {
                    LOSE -> SCISSOR_SELF
                    DRAW -> ROCK_SELF
                    WIN -> PAPER_SELF
                    else -> throw Exception("Unknown shape: $ownShape")
                }
            }
            PAPER_OPP -> {
                when (ownShape) {
                    LOSE -> ROCK_SELF
                    DRAW -> PAPER_SELF
                    WIN -> SCISSOR_SELF
                    else -> throw Exception("Unknown shape: $ownShape")
                }
            }
            SCISSOR_OPP -> {
                when (ownShape) {
                    LOSE -> PAPER_SELF
                    DRAW -> SCISSOR_SELF
                    WIN -> ROCK_SELF
                    else -> throw Exception("Unknown shape: $ownShape")
                }
            }
            else -> throw Exception("Unknown shape: $opponentShape")
        }
    }

}