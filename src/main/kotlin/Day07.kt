package com.xtb

enum class Operation {
    MULTIPLY,
    ADD,
    CONCATENATE,
    ;

    companion object {
        fun permute2(operationsCount: Int): List<List<Operation>> {
            if (operationsCount == 1) {
                return listOf(listOf(ADD), listOf(MULTIPLY))
            }
            val previousPermutations = permute2(operationsCount - 1)
            return previousPermutations.flatMap { previousPermutation ->
                listOf(ADD, MULTIPLY).map { operation ->
                    previousPermutation + operation
                }
            }
        }

        fun permute3(operationsCount: Int): List<List<Operation>> {
            if (operationsCount == 1) {
                return listOf(listOf(ADD), listOf(MULTIPLY), listOf(CONCATENATE))
            }
            val previousPermutations = permute3(operationsCount - 1)
            return previousPermutations.flatMap { previousPermutation ->
                listOf(ADD, MULTIPLY, CONCATENATE).map { operation ->
                    previousPermutation + operation
                }
            }
        }
    }
}

fun main() {
    fun part1(input: List<Pair<Long, List<Int>>>): Long {
        return input.filter { (target, numbers) ->
            Operation.permute2(numbers.size - 1).map { operations ->
                var result = numbers[0].toLong()
                for (i in 1 until numbers.size) {
                    result =
                        when (operations[i - 1]) {
                            Operation.ADD -> result + numbers[i]
                            Operation.MULTIPLY -> result * numbers[i]
                            Operation.CONCATENATE -> throw IllegalArgumentException("Invalid operation")
                        }
                }
                result
            }.firstOrNull { it == target } != null
        }.sumOf { it.first }
    }

    fun part2(input: List<Pair<Long, List<Int>>>): Long {
        return input.filter { (target, numbers) ->
            Operation.permute3(numbers.size - 1).map { operations ->
                var result = numbers[0].toLong()
                for (i in 1 until numbers.size) {
                    result =
                        when (operations[i - 1]) {
                            Operation.ADD -> result + numbers[i]
                            Operation.MULTIPLY -> result * numbers[i]
                            Operation.CONCATENATE -> result.toString().plus(numbers[i]).toLong()
                        }
                }
                result
            }.firstOrNull { it == target } != null
        }.sumOf { it.first }
    }

    val input1 =
        readInput("day07_1").map { it.split(":") }
            .map { it[0].toLong() to it[1].trim().split(" ").map { it2 -> it2.toInt() } }
    part1(input1).println()

    val input2 =
        readInput("day07_2").map { it.split(":") }
            .map { it[0].toLong() to it[1].trim().split(" ").map { it2 -> it2.toInt() } }
    part2(input2).println()
}
