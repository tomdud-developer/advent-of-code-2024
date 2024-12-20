package com.xtb

import Day11Java

fun main() {
    fun transform(num: Long): List<Long> {
        val list = mutableListOf<Long>()
        if (num == 0L) {
            list.add(1)
        } else if (num.toString().length % 2 == 0) {
            val str = num.toString()
            val leftPart = str.substring(0, str.length / 2).toLong()
            val rightPart = str.substring(str.length / 2).toLong()

            list.add(leftPart)
            list.add(rightPart)
        } else {
            list.add(num * 2024L)
        }
        return list
    }

    fun transform(list: List<Long>): List<Long> {
        val newList = mutableListOf<Long>()
        list.forEach {
            newList.addAll(transform(it))
        }
        // println(newList)
        println("len: " + newList.size)

        return newList
    }

    fun part1(inputStr: String): Int {
        val input = inputStr.split(" ").map { it.toLong() }

        var currentList = input
        for (i in 1..27) {
            transform(currentList).let {
                // Thread.sleep(1000)
                currentList = it
            }
            println(i)
            // println(currentList)
        }

        return currentList.size
    }

    val input = readInput("day11_1")
    part1(input[0]).println()

    val inputInt = input[0].split(" ").map { it.toLong() }
    Day11Java().part2(inputInt).println()
}
