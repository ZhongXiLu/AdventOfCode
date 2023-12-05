package aoc.years.year2023

import aoc.Day
import java.math.BigInteger

@Year2023
class Day05 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val seeds = input.first().substringAfter(": ").split(" ").map { it.toBigInteger() }

        // ain't nobody got time to properly parse the input
        val seedToSoil = input.toSeedRanges(3, 19)
        val soilToFertilizer = input.toSeedRanges(22, 30)
        val fertilizerToWater = input.toSeedRanges(33, 72)
        val waterToLight = input.toSeedRanges(75, 98)
        val lightToTemperature = input.toSeedRanges(101, 120)
        val temperatureToHumidity = input.toSeedRanges(123, 166)
        val humidityToLocation = input.toSeedRanges(169, 209)
        // test ranges
//        val seedToSoil = input.toSeedRanges(3, 4)
//        val soilToFertilizer = input.toSeedRanges(7, 9)
//        val fertilizerToWater = input.toSeedRanges(12, 15)
//        val waterToLight = input.toSeedRanges(18, 19)
//        val lightToTemperature = input.toSeedRanges(22, 24)
//        val temperatureToHumidity = input.toSeedRanges(27, 28)
//        val humidityToLocation = input.toSeedRanges(31, 32)

        return seeds.map { seed ->
            var mappedSeed = seed
            mappedSeed = mapSeed(seedToSoil, mappedSeed)
            mappedSeed = mapSeed(soilToFertilizer, mappedSeed)
            mappedSeed = mapSeed(fertilizerToWater, mappedSeed)
            mappedSeed = mapSeed(waterToLight, mappedSeed)
            mappedSeed = mapSeed(lightToTemperature, mappedSeed)
            mappedSeed = mapSeed(temperatureToHumidity, mappedSeed)
            mappedSeed = mapSeed(humidityToLocation, mappedSeed)
            return@map mappedSeed
        }.min()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

    private fun mapSeed(seedMaps: List<SeedRange>, seed: BigInteger): BigInteger {
        for (seedMap in seedMaps) {
            val mappedSeed = seedMap.mapSeed(seed)
            if (mappedSeed != seed) {
                return mappedSeed
            }
        }
        return seed
    }

}

private fun List<String>.toSeedRanges(from: Int, to: Int): List<SeedRange> {
    return this.subList(from, to + 1).map {
        val (destinationRange, sourceRange, rangeLength) = it.split(" ")
        return@map SeedRange(destinationRange.toBigInteger(), sourceRange.toBigInteger(), rangeLength.toBigInteger())
    }
}

private data class SeedRange(val destinationRange: BigInteger, val sourceRange: BigInteger, val rangeLength: BigInteger) {
    fun mapSeed(seed: BigInteger): BigInteger {
        if (seed >= sourceRange && seed <= sourceRange + rangeLength) {
            return destinationRange + (seed - sourceRange)
        } else {
            return seed
        }
    }
}
