package com.xtb

import java.util.stream.IntStream
import kotlin.math.abs
import kotlin.math.max
import kotlin.streams.toList

fun main() {
    fun permute(count: Int): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        for (i in 0 until count) {
            for (j in 0 until count) {
                if (i != j) {
                    result.add(i to j)
                }
            }
        }

        return result
    }

    fun part1(input: List<String>): Int {
        val antiNodess = mutableSetOf<Pair<Int, Int>>()
        input.mapIndexed { yi, line -> line.mapIndexed { xi, c -> xi to yi to c } }
            .flatten()
            .filter { it.second != '.' }
            .groupBy { it.second }
            .map { it.value }
            .forEach {
                val permutations = permute(it.size)
                permutations.forEach { perm ->
                    val cord1 = it[perm.first].first
                    val cord2 = it[perm.second].first
                    val vector = cord2.first - cord1.first to cord2.second - cord1.second
                    val antiNodesToValidate = cord1.first - vector.first to cord1.second - vector.second
                    if (antiNodesToValidate.first >= 0 && antiNodesToValidate.first < input[0].length &&
                        antiNodesToValidate.second >= 0 && antiNodesToValidate.second < input.size
                    ) {
                        antiNodess.add(antiNodesToValidate)
                    }
                }
            }
        return antiNodess.size
    }

    fun part2(input: List<String>): Int {
        val height = input.size
        val width = input[0].length
        val antiNodess = mutableSetOf<Pair<Int, Int>>()
        input.mapIndexed { yi, line -> line.mapIndexed { xi, c -> xi to yi to c } }
            .flatten()
            .filter { it.second != '.' }
            .groupBy { it.second }
            .map { it.value }
            .forEach {
                val permutations = permute(it.size)
                permutations.forEach { perm ->
                    val cord1 = it[perm.first].first
                    val cord2 = it[perm.second].first
                    val mainVector = cord2.first - cord1.first to cord2.second - cord1.second
                    val estimatedAntiNodes = max(abs(width / mainVector.first), abs(height / mainVector.second))
                    IntStream.rangeClosed(1, estimatedAntiNodes).mapToObj {
                            n ->
                        n * mainVector.first to n * mainVector.second
                    }.toList()
                        .forEach { vector ->
                            val antiNodesToValidate = cord1.first + vector.first to cord1.second + vector.second
                            if (antiNodesToValidate.first >= 0 && antiNodesToValidate.first < input[0].length &&
                                antiNodesToValidate.second >= 0 && antiNodesToValidate.second < input.size
                            ) {
                                antiNodess.add(antiNodesToValidate)
                            }
                        }
                }
            }
        return antiNodess.size
    }

    val input = readInput("day08_1")
    part1(input).println()
    part2(input).println()
}
