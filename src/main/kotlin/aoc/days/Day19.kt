package aoc.days

import aoc.Day

private val INPUT_LINE =
    "Blueprint (\\d+): Each ore robot costs (\\d+) ore. Each clay robot costs (\\d+) ore. Each obsidian robot costs (\\d+) ore and (\\d+) clay. Each geode robot costs (\\d+) ore and (\\d+) obsidian.".toRegex()

class Day19 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.map { Blueprint.of(it) }
            .sumOf { Simulation(it).run(24) }
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private class Simulation(val blueprint: Blueprint, oreRobots: Int = 1) {
    val resources = mutableMapOf("ore" to 0, "clay" to 0, "obsidian" to 0, "geode" to 0)
    val robots = mutableMapOf("ore" to oreRobots, "clay" to 0, "obsidian" to 0, "geode" to 0)

    fun run(minutes: Int): Int {
        repeat(minutes) {
            // Robots to build
            val robotToBuild = robotToBuild(listOf("geode", "obsidian", "clay", "ore")) // in this order/priority

            // Robots mine resources
            robots.forEach { (robotType, robotAmount) -> resources[robotType] = resources[robotType]!! + robotAmount }

            // Finish building robot
            if (robotToBuild != null) finishBuildingRobot(robotToBuild)
        }

        return blueprint.nr * resources["geode"]!!
    }

    private fun robotToBuild(robots: List<String>) = robots
        .firstOrNull {
            if (blueprint.robotCosts[it]!!.hasEnoughResources(resources)) {
                startBuildingRobot(it)
                return@firstOrNull true
            } else {
                return@firstOrNull false
            }
        }

    private fun startBuildingRobot(robotType: String) {
        blueprint.robotCosts[robotType]!!.costs.forEach { (resource, cost) ->
            resources[resource] = resources[resource]!! - cost
        }
    }

    private fun finishBuildingRobot(robotType: String) {
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

