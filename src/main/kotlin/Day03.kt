package com.xtb

fun main() {
    val regex1 = """mul\((\d+),(\d+)\)""".toRegex()
    val regex2 = """(mul)\((\d+),(\d+)\)|(do\(\)|don't\(\))""".toRegex()

    fun part1(input: String): Int {
        return regex1.findAll(input).sumOf { it.groupValues[1].toInt() * it.groupValues[2].toInt() }
    }

    fun part2(input: String): Int {
        data class State(var sum: Int, var isRunning: Boolean)
        return regex2.findAll(input).fold(State(0, true)) { state, matchResult ->
            when {
                matchResult.groupValues[1] == "mul" -> {
                    if (state.isRunning) {
                        state.sum += matchResult.groupValues[2].toInt() * matchResult.groupValues[3].toInt()
                    }
                    state
                }
                matchResult.groupValues[4] == "do()" -> {
                    state.isRunning = true
                    state
                }
                matchResult.groupValues[4] == "don't()" -> {
                    state.isRunning = false
                    state
                }
                else -> throw IllegalArgumentException("Invalid token. Match group: ${matchResult.groupValues}")
            }
        }.sum
    }

    val input = readInput("day03_1").joinToString(separator = "\n")
    part1(input).println()
    part2(input).println()
}
