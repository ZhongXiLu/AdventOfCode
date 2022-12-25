package aoc.days

import aoc.Day
import kotlin.math.pow

class Day25 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.sumOf { it.toDecimal() }.toSnafu()
    }

    override fun solvePart2(input: List<String>): Any {
        return "Merry Christmas 🎅"
    }

}

private fun String.toDecimal(): Long {
    return toList()
        .reversed()
        .mapIndexed { index, char ->
            val times = when (char) {
                '-' -> -1
                '=' -> -2
                else -> char.digitToInt()
            }
            return@mapIndexed 5.0.pow(index) * times
        }
        .sum()
        .toLong()
}

fun Long.toSnafu(): String {
    val base5Number = mutableListOf<Int>()

    // First convert to "normal" base5 number
    var quotient = this
    do {
        val rem = quotient.rem(5)
        quotient = quotient.div(5)
        base5Number.add(rem.toInt())
    } while (quotient != 0L)

    // Then convert any 3's and 4's
    val snafu = StringBuilder()
    var carrier = 0
    for (digit in base5Number) {
        when (val newDigit = digit + carrier) {
            5 -> {
                snafu.append("0")
                carrier = 1
            }

            4 -> {
                snafu.append("-")
                carrier = 1
            }

            3 -> {
                snafu.append("=")
                carrier = 1
            }

            else -> {
                snafu.append(newDigit)
                carrier = 0
            }
        }
    }

    return snafu.reversed().toString()
}
