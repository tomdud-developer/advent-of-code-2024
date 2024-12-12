package com.xtb

import java.util.stream.IntStream

fun main() {
    fun transform (list : List<Long>) : List<Long> {
        val newList = mutableListOf<Long>()
        list.forEach {
            if (it == 0L) {
                newList.add(1)
            } else if (it.toString().length % 2 == 0) {
                val str = it.toString()
                val leftPart = str.substring(0, str.length/2).toLong()
                val rightPart = str.substring(str.length/2).toLong()

                newList.add(leftPart)
                newList.add(rightPart)
            } else {
                newList.add(it * 2024L)
            }
        }

        return newList
    }

    fun part1(inputStr: String): Int {
        val input = inputStr.split(" ").map { it.toLong() }

        var currentList = input
        for (i in 1..75) {
            transform(currentList).let {
                currentList = it
            }
            println(i)
            //println(currentList)
        }


        return currentList.size;
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    val input = readInput("day11_1")
    part1(input[0]).println()
    part2(input).println()
}
