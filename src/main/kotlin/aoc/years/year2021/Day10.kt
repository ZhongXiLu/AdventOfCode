package aoc.years.year2021

import aoc.Day
import java.math.BigInteger
import java.util.*

val OPEN_BRACKETS = mapOf(
    ')' to '(',
    ']' to '[',
    '}' to '{',
    '>' to '<',
)

val CLOSED_BRACKETS = mapOf(
    '(' to ')',
    '[' to ']',
    '{' to '}',
    '<' to '>',
)

val INVALID_BRACKET_SCORE = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137,
)

val VALID_BRACKET_SCORE = mapOf(
    ')' to 1,
    ']' to 2,
    '}' to 3,
    '>' to 4,
)

@Year2021
class Day10 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.map { it.parse() }
            .sumOf { if (it.isSuccess) 0 else INVALID_BRACKET_SCORE[it.exceptionOrNull()!!.message!!.first()]!! }
    }

    override fun solvePart2(input: List<String>): Any {
        val scores = input.map { it.parse() }
            .filter { it.isSuccess }
            .mapNotNull { it.getOrNull() }
            .map { it.getCompletionString() }
            .map { it.getScore() }
            .sorted()

        return scores[scores.size / 2]
    }

}

private fun String.parse(): Result<String> {
    val brackets = ArrayDeque<Char>()

    for (bracket in this) {
        if (bracket in CLOSED_BRACKETS.keys) {
            brackets.push(bracket)
        } else if (brackets.pop() != OPEN_BRACKETS[bracket]) {
            return Result.failure(Exception(bracket.toString()))
        }
    }

    return Result.success(this)
}

private fun String.getCompletionString(): String {
    val brackets = ArrayDeque<Char>()
    this.forEach { if (it in CLOSED_BRACKETS.keys) brackets.push(it) else brackets.pop() }
    return brackets.map { CLOSED_BRACKETS[it] }.joinToString("")
}

private fun String.getScore(): BigInteger {
    return this.fold(BigInteger.ZERO) { score, bracket ->
        score * BigInteger.valueOf(5) + VALID_BRACKET_SCORE[bracket]!!.toBigInteger()
    }
}
