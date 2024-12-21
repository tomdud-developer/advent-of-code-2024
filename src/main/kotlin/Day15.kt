package com.xtb

import Day14Java
import Day15Java

fun main() {
    val input = readInput("day15_1")
    val moves = readInput("day15_2")
    Day15Java().part12(input, moves.joinToString(separator = "").toCharArray(), true).println()
}
