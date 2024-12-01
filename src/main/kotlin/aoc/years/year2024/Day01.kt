package aoc.years.year2024

import aoc.Day
import kotlin.math.abs

@Year2024
class Day01 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val (locationLists, firstLocationsList, secondLocationsList) = getLocations(input)

        return locationLists.indices.sumOf {
            val firstLocation = firstLocationsList[it]
            val secondLocation = secondLocationsList[it]
            return@sumOf abs(firstLocation - secondLocation)
        }
    }

    override fun solvePart2(input: List<String>): Any {
        val (locationLists, firstLocationsList, secondLocationsList) = getLocations(input)

        return locationLists.indices.sumOf {
            val firstLocation = firstLocationsList[it]
            val secondListOccurrences = secondLocationsList.count { location -> location == firstLocation }
            return@sumOf firstLocation * secondListOccurrences
        }
    }

    private fun getLocations(input: List<String>): Triple<List<List<String>>, List<Int>, List<Int>> {
        val locationLists = input.map { it.split("   ") }

        val firstLocationsList = locationLists.map { it[0].toInt() }.sorted()
        val secondLocationsList = locationLists.map { it[1].toInt() }.sorted()

        return Triple(locationLists, firstLocationsList, secondLocationsList)
    }

}