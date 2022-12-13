package aoc.days

import aoc.Day
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser.Companion.default

private val JSON_PARSER = default()

class Day13 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .chunked(3)
            .mapIndexed { nr, (p1, p2, _) ->
                val packet1 = JSON_PARSER.parse(StringBuilder(p1)) as JsonArray<Any>
                val packet2 = JSON_PARSER.parse(StringBuilder(p2)) as JsonArray<Any>
                if (packet1 compareTo packet2 < 0) nr + 1 else 0
            }
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        return ""
    }

}

private infix fun Any.compareTo(other: Any): Int {

    if (this.toString().toIntOrNull() != null && other.toString().toIntOrNull() != null) {
        return this.toString().toInt().compareTo(other.toString().toInt())
    }

    if (this is JsonArray<*> || other is JsonArray<*>) {
        val newThis = if (this is JsonArray<*>) this else JsonArray(this)
        val newOther = if (other is JsonArray<*>) other else JsonArray(other)

        for (i in newThis.indices) {
            if (i in newOther.indices) {
                val comparison = newThis[i]!! compareTo newOther[i]!!
                if (comparison == 0) {
                    continue
                }
                return comparison
            } else {
                return 1
            }
        }

        if (newThis.size == newOther.size) {
            return 0
        } else {
            return -1
        }
    }

    return 0
}
