package aoc.years.year2023

import aoc.Day
import java.math.BigInteger

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
        // test ranges
//        val seedToSoil = input.toSeedRanges(3, 4)
//        val soilToFertilizer = input.toSeedRanges(7, 9)
//        val fertilizerToWater = input.toSeedRanges(12, 15)
//        val waterToLight = input.toSeedRanges(18, 19)
//        val lightToTemperature = input.toSeedRanges(22, 24)
//        val temperatureToHumidity = input.toSeedRanges(27, 28)
//        val humidityToLocation = input.toSeedRanges(31, 32)

        return seeds.map { seed ->
            val mappedSeed = seed
            val lowestLocations = mutableSetOf<BigInteger>()
            mapSeed(seedToSoil, mappedSeed, lowestLocations)
            mapSeed(soilToFertilizer, mappedSeed, lowestLocations)
            mapSeed(fertilizerToWater, mappedSeed, lowestLocations)
            mapSeed(waterToLight, mappedSeed, lowestLocations)
            mapSeed(lightToTemperature, mappedSeed, lowestLocations)
            mapSeed(temperatureToHumidity, mappedSeed, lowestLocations)
            mapSeed(humidityToLocation, mappedSeed, lowestLocations)
            return@map lowestLocations.min()
        }.min()
    }

    private fun mapSeed(seedMaps: List<SeedMap>, seed: BigInteger): BigInteger {
        for (seedMap in seedMaps) {
            val mappedSeed = seedMap.mapSeed(seed)
            if (mappedSeed != seed) {   // found a mapping of the seed
                return mappedSeed
            }
        }
        return seed
    }

    private fun mapSeed(seedMaps: List<SeedMap>, seed: BigIntRange, lowestLocations: MutableSet<BigInteger>) {
        for (seedMap in seedMaps) {
            val newLowestLocations = mutableSetOf<BigInteger>()
            lowestLocations.forEach {
                val mappedSeed = seedMap.mapSeed(it)
                if (mappedSeed != it) {   // found a mapping of the seed
                    newLowestLocations.add(mappedSeed)
                }
            }
            newLowestLocations.forEach { lowestLocations.add(it) }

            val mappedSeed = seedMap.mapSeed(seed)
            if (mappedSeed != seed.from) {
                lowestLocations.add(mappedSeed)
            }
        }
        lowestLocations.add(seed.from)
    }

}

private fun List<String>.toSeedMaps(from: Int, to: Int): List<SeedMap> {
    return this.subList(from, to + 1).map {
        val (destinationRange, sourceRange, rangeLength) = it.split(" ")
        return@map SeedMap(destinationRange.toBigInteger(), sourceRange.toBigInteger(), rangeLength.toBigInteger())
    }
}

private data class SeedMap(val destinationRange: BigInteger, val sourceRange: BigInteger, val rangeLength: BigInteger) {
    fun mapSeed(seed: BigInteger): BigInteger {
        if (seed >= sourceRange && seed <= sourceRange + rangeLength) {
            return destinationRange + (seed - sourceRange)
        } else {
            return seed
        }
    }

    fun mapSeed(seed: BigIntRange): BigInteger {
        if (((sourceRange + rangeLength) <= seed.from) || ((seed.from + seed.range) <= sourceRange)) {
            return seed.from // no overlap
        }
        val overlapStart = maxOf(seed.from, sourceRange)
        val overlapEnd = minOf(seed.from + seed.range, sourceRange + rangeLength)
        val delta = if (seed.from < sourceRange) rangeLength - (overlapEnd - overlapStart) else overlapEnd - overlapStart
        return destinationRange + delta
    }
}

private data class BigIntRange(val from: BigInteger, val range: BigInteger)
