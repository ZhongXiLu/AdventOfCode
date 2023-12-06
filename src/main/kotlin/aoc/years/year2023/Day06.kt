package aoc.years.year2023

import aoc.Day

@Year2023
class Day06 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val times = input[0].substringAfter(":").split(" ").filter { it.isNotBlank() }.map { it.toInt() }
        val bestDistances = input[1].substringAfter(":").split(" ").filter { it.isNotBlank() }.map { it.toInt() }

        return times.indices.map { raceNr ->
            (0..times[raceNr])
                    .count { it * (times[raceNr] - it) > bestDistances[raceNr] }
        }.reduce { acc, i -> acc * i }
    }

    override fun solvePart2(input: List<String>): Any {
        val time = input[0].substringAfter(":").split(" ").filter { it.isNotBlank() }.joinToString("").toLong()
        val bestDistance = input[1].substringAfter(":").split(" ").filter { it.isNotBlank() }.joinToString("").toLong()

        return (0..time)
                .count { it * (time - it) > bestDistance }
    }

}
