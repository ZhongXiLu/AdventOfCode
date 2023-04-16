package aoc.years.year2022

import aoc.Day
import com.beust.klaxon.JsonArray
import com.beust.klaxon.Parser.Companion.default

private val JSON_PARSER = default()

@Year2022
class Day13 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .chunked(3)
            .mapIndexed { nr, (p1, p2, _) ->
                val packet1 = JSON_PARSER.parse(StringBuilder(p1)) as JsonArray<*>
                val packet2 = JSON_PARSER.parse(StringBuilder(p2)) as JsonArray<*>
                if (packet1 compareTo packet2 < 0) nr + 1 else 0
            }
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        val sortedPackets = input
            .asSequence()
            .chunked(3)
            .map { (p1, p2, _) -> listOf(p1, p2) }
            .flatten()
            .plus(listOf("[[2]]", "[[6]]"))
            .map { JSON_PARSER.parse(StringBuilder(it)) as JsonArray<*> }
            .sortedWith { p1, p2 -> p1 compareTo p2 }

        return sortedPackets.indexOfFirst { it.toString() == "JsonArray(value=[JsonArray(value=[2])])" }.plus(1)
            .times(sortedPackets.indexOfFirst { it.toString() == "JsonArray(value=[JsonArray(value=[6])])" }.plus(1))
    }

}

private infix fun Any.compareTo(other: Any): Int {
    if (this.toString().toIntOrNull() != null && other.toString().toIntOrNull() != null) {
        return this.toString().toInt().compareTo(other.toString().toInt())
    }

    if (this is JsonArray<*> || other is JsonArray<*>) {
        // Wrap in list if single element
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
                return 1         // Left side is longer
            }
        }

        return if (newThis.size == newOther.size) 0 else -1     // Equal size or right side is longer
    }

    return 0
}
