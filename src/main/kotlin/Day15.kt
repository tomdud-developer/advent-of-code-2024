package com.xtb

import Day15Java

fun main() {
    val input = readInput("day15_1")
    val moves = readInput("day15_2")
    // Day15Java().part1(input, moves.joinToString(separator = "").toCharArray()).println()
    Day15Java().part2(input, moves.joinToString(separator = "").toCharArray()).println()
}
