package com.xtb

import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val leftSide = input.map { line -> line.split("   ")[0].toInt() }.sorted()
        val rightSide = input.map { line -> line.split("   ")[1].toInt() }.sorted()
        return leftSide.zip(rightSide).sumOf { (left, right) -> abs(left - right) }
    }

    fun part2(input: List<String>): Int {
        val multipliers = input.map { line -> line.split("   ")[1].toInt() }.groupingBy { it }.eachCount()
        return input.map { line -> line.split("   ")[0].toInt() }.sumOf { multipliers.getOrDefault(it, 0) * it }
    }

    val input = readInput("day01_1")
    part1(input).println()
    part2(input).println()
}
