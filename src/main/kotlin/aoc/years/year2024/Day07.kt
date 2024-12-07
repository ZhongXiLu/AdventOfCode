package aoc.years.year2024

import aoc.Day
import java.math.BigInteger

@Year2024
class Day07 : Day() {

    override fun solvePart1(input: List<String>): Any {
        return input.map { Equation.of(it) }
            .filter { it.evaluatesToTrue() }
            .sumOf { it.total }
    }

    override fun solvePart2(input: List<String>): Any {
        return input.map { Equation.of(it) }
            .filter { it.evaluatesToTrueWithConcat() }
            .sumOf { it.total }
    }

}

private class Equation(val total: BigInteger, val operands: List<BigInteger>) {
    companion object {
        fun of(input: String): Equation {
            val equation = input.split(": ")
            val total = equation[0].toBigInteger()
            val operands = equation[1].split(" ").map(String::toBigInteger).reversed()

            return Equation(total, operands)
        }
    }

    fun evaluatesToTrue(currentTotal: BigInteger = this.total): Boolean {
        if (operands.size == 1) {
            return currentTotal == operands[0]
        }

        val remainingOperands = operands.subList(1, operands.size)
        val plusOperation = Equation(currentTotal - operands[0], remainingOperands)
        val multiplyOperation = Equation(
            if (currentTotal % operands[0] == BigInteger.ZERO) currentTotal / operands[0] else BigInteger.valueOf(-1),
            remainingOperands
        )

        return plusOperation.evaluatesToTrue() || multiplyOperation.evaluatesToTrue()
    }

    fun evaluatesToTrueWithConcat(currentTotal: BigInteger = this.total): Boolean {
        if (operands.size == 1) {
            return currentTotal == operands[0]
        }

        val remainingOperands = operands.subList(1, operands.size)
        val plusOperation = Equation(currentTotal - operands[0], remainingOperands)
        val multiplyOperation = Equation(
            if (currentTotal % operands[0] == BigInteger.ZERO) currentTotal / operands[0] else BigInteger.valueOf(-1),
            remainingOperands
        )

        val totalString = currentTotal.toString()
        val operandString = operands[0].toString()

        if (currentTotal >= BigInteger.ZERO && totalString.endsWith(operandString)) {
            if (totalString == operandString) {
                return true
            }

            if (totalString.length > operandString.length) {
                val newTotal = totalString.substring(0, totalString.length - operandString.length).toBigInteger()

                val concatOperation = Equation(newTotal, remainingOperands)
                return plusOperation.evaluatesToTrueWithConcat() || multiplyOperation.evaluatesToTrueWithConcat() || concatOperation.evaluatesToTrueWithConcat()
            }
        }

        return plusOperation.evaluatesToTrueWithConcat() || multiplyOperation.evaluatesToTrueWithConcat()
    }

}