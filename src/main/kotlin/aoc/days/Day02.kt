package aoc.days

import aoc.Day

private const val ROCK_OPP = "A"
private const val PAPER_OPP = "B"
private const val SCISSOR_OPP = "C"
private const val ROCK_SELF = "X"
private const val PAPER_SELF = "Y"
private const val SCISSOR_SELF = "Z"
private const val LOSE_SCORE = 0
private const val DRAW_SCORE = 3
private const val WIN_SCORE = 6
private const val LOSE = "X"
private const val DRAW = "Y"
private const val WIN = "Z"

private val SCORE_OUTCOMES = hashMapOf<String, Map<String, Int>>(
    ROCK_OPP to hashMapOf(SCISSOR_SELF to LOSE_SCORE, ROCK_SELF to DRAW_SCORE, PAPER_SELF to WIN_SCORE),
    PAPER_OPP to hashMapOf(ROCK_SELF to LOSE_SCORE, PAPER_SELF to DRAW_SCORE, SCISSOR_SELF to WIN_SCORE),
    SCISSOR_OPP to hashMapOf(PAPER_SELF to LOSE_SCORE, SCISSOR_SELF to DRAW_SCORE, ROCK_SELF to WIN_SCORE)
)

private val SHAPE_SELECTION = hashMapOf<String, Map<String, String>>(
    ROCK_OPP to hashMapOf(LOSE to SCISSOR_SELF, DRAW to ROCK_SELF, WIN to PAPER_SELF),
    PAPER_OPP to hashMapOf(LOSE to ROCK_SELF, DRAW to PAPER_SELF, WIN to SCISSOR_SELF),
    SCISSOR_OPP to hashMapOf(LOSE to PAPER_SELF, DRAW to SCISSOR_SELF, WIN to ROCK_SELF)
)

class Day02 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .map { it.split(" ") }
            .map { (opponentShape, ownShape) ->
                calculateScoreOutcome(opponentShape, ownShape) + calculateScoreShape(ownShape)
            }
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .map { it.split(" ") }
            .map { (opponentShape, ownShape) -> listOf(opponentShape, chooseOwnShape(opponentShape, ownShape)) }
            .map { (opponentShape, ownShape) ->
                calculateScoreOutcome(opponentShape, ownShape) + calculateScoreShape(ownShape)
            }
            .sum()
    }

    private fun calculateScoreOutcome(opponentShape: String, ownShape: String): Int {
        return SCORE_OUTCOMES[opponentShape]!![ownShape]!!
    }

    private fun calculateScoreShape(shape: String): Int {
        return when (shape) {
            ROCK_SELF -> 1
            PAPER_SELF -> 2
            SCISSOR_SELF -> 3
            else -> throw Exception("Unknown shape: $shape")
        }
    }

    private fun chooseOwnShape(opponentShape: String, ownShape: String): String {
        return SHAPE_SELECTION[opponentShape]!![ownShape]!!
    }

}