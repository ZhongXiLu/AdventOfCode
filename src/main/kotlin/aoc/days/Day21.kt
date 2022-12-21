package aoc.days

import aoc.Day
import org.mariuszgromada.math.mxparser.Expression

class Day21 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val monkeys = input.map { it.split(": ") }.associate { (name, expr) -> Pair(name, expr) }.toMutableMap()
        monkeys.flattenRootExpression()

        return monkeys.getRootExpressionResult()
    }

    override fun solvePart2(input: List<String>): Any {
        return 0
    }

}

private fun MutableMap<String, String>.getRootExpressionResult(): Long {
    return Expression(this["root"]).calculate().toLong()
}

private fun MutableMap<String, String>.flattenRootExpression() {
    while (this["root"]!!.contains("[a-zA-Z]".toRegex())) {
        this["root"] = this["root"]!!
            .split(" ")
            .map { if (it.contains("[a-zA-Z]".toRegex())) return@map "( ${this[it]} )" else return@map it }
            .joinToString(" ")
    }
}
