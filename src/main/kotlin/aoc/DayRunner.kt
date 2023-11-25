package aoc

import org.reflections.Reflections

fun main(args: Array<String>) {
    val dayRunner = DayRunner()

    if (args.size == 2) {
        dayRunner.run(args[0], args[1])
    } else {
        dayRunner.runAll()
    }
}

@Suppress("UNCHECKED_CAST")
class DayRunner {

    private val reflections = Reflections("aoc")

    fun run(year: String, dayNr: String) {
        val yearAnnotation = Class.forName("aoc.years.year$year.Year$year") as Class<out Annotation>

        val dayClass = reflections.getTypesAnnotatedWith(yearAnnotation)
            .find { it.simpleName.contains("Day${dayNr.padStart(2, '0')}") }
            ?.asSubclass(Day::class.java)

        if (dayClass != null) {
            runDay(year, dayNr, dayClass.instance())
        } else {
            throw IllegalArgumentException("Cannot find class for day $dayNr")
        }
    }

    fun runAll() {
        reflections.getSubTypesOf(Day::class.java)
            .sortedWith(compareBy(Class<out Day>::getYear, Class<out Day>::getSimpleName))
            .forEach { runDay(it.getYear(), it.getDayNr(), it.instance()) }
    }

    private fun runDay(year: String, dayNr: String, day: Day) {
        val input = DayRunner::class.java.getResourceAsStream("year$year/Day${dayNr.padStart(2, '0')}.txt")
            ?.bufferedReader()
            ?.readLines()

        if (input != null) {
            val solutionPart1 = day.solvePart1(input)
            val solutionPart2 = day.solvePart2(input)

            printSolutions(year, dayNr, solutionPart1, solutionPart2)
        } else {
            throw IllegalArgumentException("Cannot find input file for year $year day $dayNr")
        }
    }

    private fun printSolutions(year: String, day: String, solutionPart1: Any, solutionPart2: Any) {
        println("\nYear $year Day $day")
        println("Part 1:\t$solutionPart1")
        println("Part 2:\t$solutionPart2")
    }
}

private fun Class<out Day>.getDayNr(): String {
    return this.simpleName.replace("Day", "").trimStart('0')
}

private fun Class<out Day>.getYear(): String {
    return this.annotations.first { annotation -> annotation.toString().contains("Year") }
        .toString().substringAfter("Year").removeSuffix("()")
}

private fun Class<out Day>.instance(): Day {
    return this.constructors[0].newInstance() as Day
}
