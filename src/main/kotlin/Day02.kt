package com.xtb

import kotlin.math.abs

fun main() {
    fun validateDeltas(deltas: List<Int>): Boolean {
        return deltas.all { abs(it) in 1..3 } && (deltas.all { it > 0 } || deltas.all { it < 0 })
    }

    fun part1(input: List<String>): Int {
        return input.map { line -> line.split(" ").map { it.toInt() }.zipWithNext { a, b -> b - a } }
            .map { validateDeltas(it) }
            .map { if (it) 1 else 0 }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.map { line -> line.split(" ").map { it.toInt() } }
            .map { line ->
                validateDeltas(line.zipWithNext { a, b -> b - a }) ||
                    (
                        line.indices.map { index ->
                            validateDeltas(line.filterIndexed { i, _ -> i != index }.zipWithNext { a, b -> b - a })
                        }.firstOrNull { it } ?: false
                    )
            }
            .map { if (it) 1 else 0 }.sum()
    }

    val input = readInput("day02_1")
    part1(input).println()
    part2(input).println()
}
