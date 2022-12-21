package aoc.days

import aoc.Day
import org.mariuszgromada.math.mxparser.Expression

class Day21 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input
            .map { it.split(": ") }
            .associate { (name, expr) -> Pair(name, expr) }
            .toMutableMap()
            .getRootExpressionResult()
    }

    override fun solvePart2(input: List<String>): Any {
        return input
            .map { it.split(": ") }
            .associate { (name, expr) -> Pair(name, expr) }
            .toMutableMap()
            .getHumanExpressionForRootExpressionToBeEqual()
    }

}

private fun MutableMap<String, String>.getRootExpressionResult(): Long {
    this.flattenRootExpression()
    return Expression(this["root"]).calculate().toLong()
}

private fun MutableMap<String, String>.getHumanExpressionForRootExpressionToBeEqual(): Long {
    this["humn"] = "?"
    this["root"] = this["root"]!!.replace("[+\\-*/]".toRegex(), "-")
    this.flattenRootExpression()
    this["root"] = this["root"]!!.replace("?", "x")
    return Expression("solve(${this["root"]}, x, -9999999999999, 9999999999999)").calculate().toLong()
}

private fun MutableMap<String, String>.flattenRootExpression() {
    while (this["root"]!!.contains("[a-zA-Z]".toRegex())) {
        this["root"] = this["root"]!!
            .split(" ")
            .map { if (it.contains("[a-zA-Z]".toRegex())) return@map "( ${this[it]} )" else return@map it }
            .joinToString(" ")
    }
}
