package com.xtb

import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
return input.map { line -> line.split(" ").map { it.toInt() }.zipWithNext { a, b -> b - a } }
    .map { deltas ->
        if (deltas.all { abs(it) in 1..3 } && (deltas.all { it > 0 } || deltas.all { it < 0 })) 1 else 0 }.sum()




            /*.map {
            var deltaPrev = it[0] - it[1]
            for (i in 2 until it.size) {
                val deltaCurrent = it[i-1] - it[i]
                if (abs(deltaCurrent) < 1 || abs(deltaCurrent) > 3) {
                    return@map 0;
                }
                if (deltaPrev * deltaCurrent < 0) {
                    return@map 0;
                }
                deltaPrev = deltaCurrent
            }
            1
        }.sum()*/
    }

    fun part2(input: List<String>): Int {
        return 0;
    }

    val input = readInput("day02_1")
    part1(input).println()
    part2(input).println()
}