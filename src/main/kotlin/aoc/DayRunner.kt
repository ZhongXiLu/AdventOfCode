package aoc

import org.reflections.Reflections

class DayRunner {

    private val reflections = Reflections("aoc")

    fun run(dayNr: String) {
        val dayClass = reflections.getSubTypesOf(Day::class.java)
            .find { it.simpleName.contains(dayNr.padStart(2, '0')) }

        if (dayClass != null) {
            runDay(dayNr, dayClass)
        } else {
            throw Exception("Cannot find class for day $dayNr")
        }
    }

    fun runAll() {
        reflections.getSubTypesOf(Day::class.java)
            .forEach {
                val dayNr = it.simpleName.replace("Day", "").trimStart('0')
                runDay(dayNr, it)
            }
    }

    private fun runDay(dayNr: String, dayClass: Class<out Day>) {
        val input =
            DayRunner::class.java.getResourceAsStream("Day${dayNr.padStart(2, '0')}.txt")?.bufferedReader()?.readLines()

        val day = dayClass.constructors[0].newInstance() as Day
        if (input != null) {
            val solutionPart1 = day.solvePart1(input)
            val solutionPart2 = day.solvePart2(input)

            printSolutions(dayNr, solutionPart1, solutionPart2)
        } else {
            throw Exception("Cannot find input file for day $dayNr")
        }
    }

    private fun printSolutions(day: String, solutionPart1: Any, solutionPart2: Any) {
        println("\nDay $day")
        println("Part 1:\t$solutionPart1")
        println("Part 2:\t$solutionPart2")
    }
}

fun main(args: Array<String>) {
    val dayRunner = DayRunner()

    if (args.size == 1) {
        dayRunner.run(args[0])
    } else {
        dayRunner.runAll()
    }
}
