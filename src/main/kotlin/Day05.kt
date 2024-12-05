package com.xtb

fun main() {
    fun isValid(
        update: List<Int>,
        mapOfRules: Map<Int, List<Int>>,
    ): Boolean {
        val left = mutableSetOf<Int>()
        update.forEach {
            val listOfMustNotBefore = mapOfRules[it] ?: listOf()
            for (element in listOfMustNotBefore) {
                if (left.contains(element)) {
                    return false
                }
            }
            left.add(it)
        }
        return true
    }

    fun generateMapOfRules(rules: List<String>): Map<Int, List<Int>> {
        return rules.map { it.split("|").map(String::toInt) }
            .groupBy({ it[0] }, { it[1] })
    }

    fun part1(
        rules: List<String>,
        updates: List<String>,
    ): Int {
        val mapOfRules: Map<Int, List<Int>> = generateMapOfRules(rules)
        return updates.map { it.split(",").map(String::toInt) }
            .filter { isValid(it, mapOfRules) }.sumOf {
                it[(it.size - 1) / 2]
            }
    }

    fun part2(
        rules: List<String>,
        updates: List<String>,
    ): Int {
        val mapOfRules: Map<Int, List<Int>> = generateMapOfRules(rules)

        fun makeValid(invalid: List<Int>): List<Int> {
            val left = mutableSetOf<Int>()
            for (i in invalid.indices) {
                val element = invalid[i]
                val listOfMustNotBefore = mapOfRules[element] ?: listOf()
                for (mustNotBefore in listOfMustNotBefore) {
                    if (left.contains(mustNotBefore)) {
                        val mutableList = invalid.toMutableList()
                        mutableList[i] = mutableList[i - 1]
                        mutableList[i - 1] = element
                        return makeValid(mutableList)
                    }
                }
                left.add(element)
            }
            return invalid
        }

        return updates.map { it.split(",").map(String::toInt) }
            .filter { !isValid(it, mapOfRules) }.map { makeValid(it) }.sumOf {
                it[(it.size - 1) / 2]
            }
    }

    val input = readInput("day05_1")
    val separatorLine = input.indexOf("x")
    val rules = input.subList(0, separatorLine)
    val updates = input.subList(separatorLine + 1, input.size)
    part1(rules, updates).println()
    part2(rules, updates).println()
}
