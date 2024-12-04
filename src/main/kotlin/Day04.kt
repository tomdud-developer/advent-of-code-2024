package com.xtb

fun main() {
    fun part1(matrix: List<String>): Int {
        val w = matrix[0].length
        val h = matrix.size

        val leftCrosses = mutableListOf<String>()
        for (d in 0 until (h + w - 1)) {
            val diagonal = StringBuilder()
            for (i in 0 until h) {
                val j = d - i
                if (j in 0 until w) {
                    diagonal.append(matrix[i][j])
                }
            }
            if (diagonal.isNotEmpty()) {
                leftCrosses.add(diagonal.toString())
            }
        }
        println(leftCrosses)

        val rightCrosses = mutableListOf<String>()
        for (d in 0 until (h + w - 1)) {
            val diagonal = StringBuilder()
            for (i in 0 until h) {
                val j = w - 1 - d + i
                if (j in 0 until w) {
                    diagonal.append(matrix[i][j])
                }
            }
            if (diagonal.isNotEmpty()) {
                rightCrosses.add(diagonal.toString())
            }
        }
        val verticals = mutableListOf<String>()
        for (x in 0 until w) {
            val vertical = StringBuilder()
            for (y in 0 until h) {
                vertical.append(matrix[y][x])
            }
            verticals.add(vertical.toString())
        }
        println(verticals)

        val regex1 = """XMAS""".toRegex()
        val regex2 = """SAMX""".toRegex()

        println("in matrix found" + matrix.map { regex1.findAll(it).count() + regex2.findAll(it).count() }.sum())
        println("in verticals found" + verticals.map { regex1.findAll(it).count() + regex2.findAll(it).count() }.sum())
        println("in leftCrosses found" + leftCrosses.map { regex1.findAll(it).count() + regex2.findAll(it).count() }.sum())
        println("in rightCrosses found" + rightCrosses.map { regex1.findAll(it).count() + regex2.findAll(it).count() }.sum())

        val concatenated = (leftCrosses + rightCrosses + verticals + matrix).joinToString(separator = " ")
        println(concatenated)
        return regex1.findAll(concatenated).count() + regex2.findAll(concatenated).count()
    }

    fun part2(input: List<String>): Int {
        val w = input[0].length
        val h = input.size
        var count = 0
        for (x in 1 until w - 1) {
            for (y in 1 until h - 1) {
                if (input[y][x] == 'A') {
                    val str = "${input[y - 1][x - 1]}${input[y - 1][x + 1]}${input[y + 1][x - 1]}${input[y + 1][x + 1]}"
                    if (str == "MMSS" || str == "MSMS" || str == "SMSM" || str == "SSMM") {
                        count++
                    }
                }
            }
        }
        return count
    }

    val input = readInput("day04_1")
    part1(input).println()
    part2(input).println()
}
