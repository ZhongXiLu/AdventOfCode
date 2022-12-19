package aoc.days

import aoc.Day

private val INPUT_LINE =
    "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.".toRegex()

class Day19 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.map { Blueprint.of(it) }.sumOf {
            it.nr * Simulation(it).getMaxPossibleGeode(
                24,
                mutableMapOf("ore" to 0, "clay" to 0, "obsidian" to 0, "geode" to 0),
                mutableMapOf("ore" to 1, "clay" to 0, "obsidian" to 0, "geode" to 0)
            )
        }
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private class Simulation(val blueprint: Blueprint) {
    fun getMaxPossibleGeode(minutes: Int, resources: MutableMap<String, Int>, robots: MutableMap<String, Int>): Int {
        if (minutes == 0) {
            return resources["geode"]!!
        }

        val possibleRobotsToBuild = robotToBuild(resources, listOf("geode", "obsidian", "clay", "ore"))

        return possibleRobotsToBuild
            .plus(null) // build no robot
            .parallelStream()
            .map {
                val (newResources, newRobots, newRobot) = Triple(HashMap(resources), HashMap(robots), it)

                if (newRobot != null) startBuildingRobot(newResources, newRobot)
                newRobots.forEach { (robotType, robotAmount) ->
                    newResources[robotType] = newResources[robotType]!! + robotAmount
                }
                if (newRobot != null) finishBuildingRobot(newRobots, newRobot)

                return@map Pair(newResources, newRobots)
            }
            .map { (resources, robots) -> getMaxPossibleGeode(minutes - 1, resources, robots) }
            .max { g1, g2 -> g1 - g2 }
            .get()
    }

    private fun robotToBuild(resources: MutableMap<String, Int>, robots: List<String>) = robots
        .filter { blueprint.robotCosts[it]!!.hasEnoughResources(resources) }

    private fun startBuildingRobot(resources: MutableMap<String, Int>, robotType: String) {
        blueprint.robotCosts[robotType]!!.costs.forEach { (resource, cost) ->
            resources[resource] = resources[resource]!! - cost
        }
    }

    private fun finishBuildingRobot(robots: MutableMap<String, Int>, robotType: String) {
        robots[robotType] = robots[robotType]!! + 1
    }
}

private data class Blueprint(
    val nr: Int,
    val robotCosts: Map<String, RobotCost>
) {
    companion object {
        fun of(line: String): Blueprint {
            val (
                nr,
                oreRobotOreCost,
                clayRobotOreCost,
                obsidianRobotOreCost, obsidianRobotClayCost,
                geodeRobotOreCost, geodeRobotObsidianCost
            ) = INPUT_LINE.find(line)!!.destructured
            return Blueprint(
                nr.toInt(),
                mapOf(
                    "ore" to RobotCost(mapOf("ore" to oreRobotOreCost.toInt())),
                    "clay" to RobotCost(mapOf("ore" to clayRobotOreCost.toInt())),
                    "obsidian" to RobotCost(
                        mapOf(
                            "ore" to obsidianRobotOreCost.toInt(),
                            "clay" to obsidianRobotClayCost.toInt()
                        )
                    ),
                    "geode" to RobotCost(
                        mapOf(
                            "ore" to geodeRobotOreCost.toInt(),
                            "obsidian" to geodeRobotObsidianCost.toInt()
                        )
                    )
                )
            )
        }
    }
}

private data class RobotCost(val costs: Map<String, Int>) {
    fun hasEnoughResources(resources: Map<String, Int>): Boolean {
        return costs.all { (resource, cost) -> resources[resource]!! >= cost }
    }
}

