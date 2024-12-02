package com.xtb

import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line -> line.split(" ").map { it.toInt() }.zipWithNext { a, b -> b - a } }
            .map { deltas -> (deltas.all { abs(it) in 1..3 } && (deltas.all { it > 0 } || deltas.all { it < 0 }))}
            .map { if (it) 1 else 0 }.sum()
    }

    fun part2(input: List<String>): Int {
        return 0;
    }

    val input = readInput("day02_1")
    part1(input).println()
    part2(input).println()
}