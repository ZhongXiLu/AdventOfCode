package aoc.years.year2022

import aoc.Day

@Year2022
class Day08 : Day() {

    override fun solvePart1(input: List<String>): Any {
        val forest = buildForest(input)

        return forest
            .mapIndexed { row, trees ->
                trees.mapIndexed { column, _ -> forest.isTreeVisible(row, column) }.count { it }
            }
            .sum()
    }

    override fun solvePart2(input: List<String>): Any {
        val forest = buildForest(input)

        return forest
            .mapIndexed { row, trees ->
                trees.mapIndexed { column, _ -> forest.getScenicScore(row, column) }.max()
            }
            .max()
    }

    private fun buildForest(input: List<String>): List<List<Int>> {
        return input
            .map { it.toCharArray() }
            .map { it.map { digit -> digit.digitToInt() } }
    }

    private fun List<List<Int>>.isTreeVisible(row: Int, column: Int): Boolean {
        return this.isVisibleFromTop(row, column)
                || this.isVisibleFromBottom(row, column)
                || this.isVisibleFromLeft(row, column)
                || this.isVisibleFromRight(row, column)
    }

    private fun List<List<Int>>.isVisibleFromTop(row: Int, column: Int): Boolean {
        return (0 until row).all { this[it][column] < this[row][column] }
    }

    private fun List<List<Int>>.isVisibleFromBottom(row: Int, column: Int): Boolean {
        return (row + 1 until this.size).all { this[it][column] < this[row][column] }
    }

    private fun List<List<Int>>.isVisibleFromLeft(row: Int, column: Int): Boolean {
        return (0 until column).all { this[row][it] < this[row][column] }
    }

    private fun List<List<Int>>.isVisibleFromRight(row: Int, column: Int): Boolean {
        return (column + 1 until this.first().size).all { this[row][it] < this[row][column] }
    }

    private fun List<List<Int>>.getScenicScore(row: Int, column: Int): Int {
        return this.getScenicScoreToTop(row, column)
            .times(this.getScenicScoreToBottom(row, column))
            .times(this.getScenicScoreToLeft(row, column))
            .times(this.getScenicScoreToRight(row, column))
    }

    private fun List<List<Int>>.getScenicScoreToTop(row: Int, column: Int): Int {
        val trees = row - 1 downTo 0
        val treesInSight = trees.takeWhile { this[it][column] < this[row][column] }
        return treesInSight.count().plus(if (treesInSight.size == trees.count()) 0 else 1)
    }

    private fun List<List<Int>>.getScenicScoreToBottom(row: Int, column: Int): Int {
        val trees = row + 1 until this.size
        val treesInSight = trees.takeWhile { this[it][column] < this[row][column] }
        return treesInSight.count().plus(if (treesInSight.size == trees.count()) 0 else 1)
    }

    private fun List<List<Int>>.getScenicScoreToLeft(row: Int, column: Int): Int {
        val trees = column - 1 downTo 0
        val treesInSight = trees.takeWhile { this[row][it] < this[row][column] }
        return treesInSight.count().plus(if (treesInSight.size == trees.count()) 0 else 1)
    }

    private fun List<List<Int>>.getScenicScoreToRight(row: Int, column: Int): Int {
        val trees = column + 1 until this.first().size
        val treesInSight = trees.takeWhile { this[row][it] < this[row][column] }
        return treesInSight.count().plus(if (treesInSight.size == trees.count()) 0 else 1)
    }

}
