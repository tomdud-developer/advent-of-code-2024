package com.xtb

import JavaDay09
import java.util.stream.IntStream

fun main() {
    fun part1(inputStr: String): Long {
        data class Location(val value: Int) {
            fun isEmpty(): Boolean {
                return value == -1
            }
        }
        val input = inputStr.trim().split("").filter { it.isNotEmpty() }.map { it.toInt() }

        var isFile = true
        var id = 0
        val memory = mutableListOf<Location>()

        input.forEach {
            if (isFile) {
                IntStream.rangeClosed(1, it).forEach { memory.add(Location(id)) }
                id++
            } else {
                IntStream.rangeClosed(1, it).forEach { memory.add(Location(-1)) }
            }
            isFile = !isFile
        }

        var lp = 0
        var rp = memory.size - 1
        while (lp < rp) {
            if (!memory[lp].isEmpty()) {
                lp++
            } else if (memory[rp].isEmpty()) {
                rp--
            } else {
                memory[lp] = memory[rp]
                memory[rp] = Location(-1)
                lp++
                rp--
            }
        }

        return memory.filter { !it.isEmpty() }.mapIndexed { i, c ->
            i * c.value.toLong()
        }.sum()
    }

    fun convertInput(inputStr: String): List<Int> {
        val input = inputStr.trim().split("").filter { it.isNotEmpty() }.map { it.toInt() }

        var isFile = true
        var id = 0
        val memory = mutableListOf<Int>()

        input.forEach {
            if (isFile) {
                IntStream.rangeClosed(1, it).forEach { memory.add(id) }
                id++
            } else {
                IntStream.rangeClosed(1, it).forEach { memory.add((-1)) }
            }
            isFile = !isFile
        }
        return memory
    }


    // Not working, i decide to write it in Java
    fun part2(inputStr: String): Long {
        data class Location(val value: Int, val size: Int, var prev: Location? = null, var next: Location? = null) {
            fun isEmpty(): Boolean {
                return value == -1
            }
        }
        val input = inputStr.trim().split("").filter { it.isNotEmpty() }.map { it.toInt() }

        val memoryFirst = Location(0, input[0])
        var isFile = false
        var id = 1
        var prevLocation = memoryFirst
        input.drop(1).forEach {
            if (isFile) {
                val newLocation = Location(id, it, prevLocation)
                prevLocation.next = newLocation
                prevLocation = newLocation
                id++
            } else {
                val newLocation = Location(-1, it, prevLocation)
                prevLocation.next = newLocation
                prevLocation = newLocation
            }
            isFile = !isFile
        }
        val memoryLast = prevLocation

        var lp = memoryFirst
        var rp = memoryLast
        while (lp.next != rp.next) {
            if (!lp.isEmpty()) {
                println("Go to next lp")
                lp = lp.next!!
            } else if (rp.isEmpty()) {
                println("Go to prev rp")
                rp = rp.prev!!
            } else {
                val freeSpaceAfter = lp.size - rp.size
                if (freeSpaceAfter >= 0) {
                    println(
                        "Found freeSpaceAfter: $freeSpaceAfter with length ${rp.size}," +
                            " try to replace with size ${rp.size} and val ${rp.value}",
                    )
                    val leftLeftChainLink = lp.prev
                    val leftRightChainLink = lp.next
                    val rightLeftChainLink = rp.prev
                    val rightRightChainLink = rp.next

                    if (freeSpaceAfter == 0) {
                        leftLeftChainLink?.next = rp
                        rp.prev = leftLeftChainLink
                        rp.next = leftRightChainLink
                        leftRightChainLink?.prev = rp

                        rightLeftChainLink?.next = lp
                        lp.prev = rightLeftChainLink
                        lp.next = rightRightChainLink
                        rightRightChainLink?.prev = lp

                        val tmp = lp
                        lp = rp
                        rp = tmp

                        lp = lp.next!!
                        rp = rp.prev!!
                    } else {
                        leftLeftChainLink?.next = rp
                        rp.prev = leftLeftChainLink
                        val newLeftFreeLocation = Location(-1, freeSpaceAfter, rp, leftRightChainLink)
                        rp.next = newLeftFreeLocation
                        leftRightChainLink?.prev = newLeftFreeLocation

                        val newRightFreeLocation = Location(-1, rp.size, rightLeftChainLink, rightRightChainLink)
                        rightLeftChainLink?.next = newRightFreeLocation
                        rightRightChainLink?.prev = newRightFreeLocation

                        val tmp = lp
                        lp = rp
                        rp = tmp

                        lp = lp.next!!
                        rp = rp.prev!!
                    }
                } else {
                    // search next empty space
                    println("Search next empty space because previous was too small")
                    lp = lp.next ?: break
                }

                // lp = lp.next?: break
                // rp = rp.prev?: break
                Thread.sleep(1000)
            }
        }

        var sum = 0L
        var i = 0L
        var current = memoryFirst

        while (current.next != null) {
            if (!current.isEmpty()) {
                sum += i * current.value
                i++
            }
            current = current.next!!
        }

        return sum
    }

    val input = readInput("day09_1")

    // part1(input[0]).println()
    // part2(input[0]).println()
    // part2_seo(input[0]).println()

    JavaDay09().part2(convertInput(input[0])).println()
}
