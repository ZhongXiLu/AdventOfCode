package aoc.years.year2023

import aoc.Day
import java.math.BigInteger
import kotlin.math.min

@Year2023
class Day05 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val seeds = input.first().substringAfter(": ").split(" ").map { it.toBigInteger() }

        // ain't nobody got time to properly parse the input
        val seedToSoil = input.toSeedMaps(3, 19)
        val soilToFertilizer = input.toSeedMaps(22, 30)
        val fertilizerToWater = input.toSeedMaps(33, 72)
        val waterToLight = input.toSeedMaps(75, 98)
        val lightToTemperature = input.toSeedMaps(101, 120)
        val temperatureToHumidity = input.toSeedMaps(123, 166)
        val humidityToLocation = input.toSeedMaps(169, 209)

        return seeds.map { seed ->
            return@map seed.map(seedToSoil)
                .map(soilToFertilizer)
                .map(fertilizerToWater)
                .map(waterToLight)
                .map(lightToTemperature)
                .map(temperatureToHumidity)
                .map(humidityToLocation)
        }.min()
    }

    override fun solvePart2(input: List<String>): Any {
        val seeds = input.first().substringAfter(": ").split(" ")
            .chunked(2)
            .map { BigIntRange(it.component1().toBigInteger(), it.component2().toBigInteger()) }

        // ain't nobody got time to properly parse the input
        val seedToSoil = input.toSeedMaps(3, 19)
        val soilToFertilizer = input.toSeedMaps(22, 30)
        val fertilizerToWater = input.toSeedMaps(33, 72)
        val waterToLight = input.toSeedMaps(75, 98)
        val lightToTemperature = input.toSeedMaps(101, 120)
        val temperatureToHumidity = input.toSeedMaps(123, 166)
        val humidityToLocation = input.toSeedMaps(169, 209)

        var minLocation = seeds.first().from

        for (seed in seeds) {
            var curSeed = seed.from

            while (curSeed < seed.from + seed.range) {
                val mappedSeed = curSeed.map(seedToSoil)
                    .map(soilToFertilizer)
                    .map(fertilizerToWater)
                    .map(waterToLight)
                    .map(lightToTemperature)
                    .map(temperatureToHumidity)
                    .map(humidityToLocation)
                minLocation = minOf(minLocation, mappedSeed)
                curSeed++
            }
        }

        return minLocation
    }

}

private fun BigInteger.map(seedMaps: List<SeedMap>): BigInteger {
    for (seedMap in seedMaps) {
        val mappedSeed = seedMap.mapSeed(this)
        if (mappedSeed != this) {   // found a mapping of the seed
            return mappedSeed
        }
    }
    return this
}

private fun List<String>.toSeedMaps(from: Int, to: Int): List<SeedMap> {
    return this.subList(from, to + 1).map {
        val (destinationRange, sourceRange, rangeLength) = it.split(" ")
        return@map SeedMap(destinationRange.toBigInteger(), sourceRange.toBigInteger(), rangeLength.toBigInteger())
    }
}

private data class SeedMap(val destinationRange: BigInteger, val sourceRange: BigInteger, val rangeLength: BigInteger) {
    fun mapSeed(seed: BigInteger): BigInteger {
        if (seed >= sourceRange && seed < sourceRange + rangeLength) {
            return destinationRange + (seed - sourceRange)
        } else {
            return seed
        }
    }
}

private data class BigIntRange(val from: BigInteger, val range: BigInteger)
