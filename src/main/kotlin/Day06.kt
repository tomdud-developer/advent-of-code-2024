package com.xtb

fun main() {
    fun rotateDirection(direction: Char) =
        when (direction) {
            '^' -> '>'
            'v' -> '<'
            '<' -> '^'
            '>' -> 'v'
            else -> throw IllegalArgumentException("Invalid direction")
        }

    fun getDxy(direction: Char) =
        when (direction) {
            '^' -> Pair(0, -1)
            'v' -> Pair(0, 1)
            '<' -> Pair(-1, 0)
            '>' -> Pair(1, 0)
            else -> throw IllegalArgumentException("Invalid direction")
        }

    fun part1(input: List<String>): Set<Pair<Int, Int>> {
        val w = input[0].length
        val h = input.size

        var currentPosition: Pair<Pair<Int, Int>, Char> = Pair(Pair(0, 0), ' ')
        for (h1 in 0 until h) {
            for (w1 in 0 until w) {
                if (input[h1][w1] == '^' || input[h1][w1] == 'v' || input[h1][w1] == '<' || input[h1][w1] == '>') {
                    currentPosition = Pair(Pair(w1, h1), input[h1][w1])
                }
            }
        }
        println(currentPosition)
        val positionsWhereBeen = mutableSetOf<Pair<Int, Int>>()
        positionsWhereBeen.add(currentPosition.first)
        while (true) {
            val (position, direction) = currentPosition
            val (x, y) = position
            val (dx, dy) = getDxy(direction)
            if (x + dx < 0 || x + dx >= w || y + dy < 0 || y + dy >= h) {
                break
            }
            if (input[y + dy][x + dx] == '#') {
                val newDirection = rotateDirection(direction)
                println(newDirection)
                currentPosition = Pair(position, newDirection)
                continue
            }

            val newPosition = Pair(Pair(x + dx, y + dy), direction)
            positionsWhereBeen.add(newPosition.first)
            println(newPosition)
            currentPosition = newPosition
        }

        return positionsWhereBeen
    }

    fun part2(input: List<String>): Int {
        val positionsWhereBeenInPart1 = part1(input)

        val progressIterations = positionsWhereBeenInPart1.size - 1
        var progress = 0
        return positionsWhereBeenInPart1.drop(1).filter { positionWhereBeen ->
            println("Progress:" + progress++ / progressIterations.toDouble())
            val space = input.toMutableList()

            val rowIndex = positionWhereBeen.second
            val columnIndex = positionWhereBeen.first

            val updatedRow = space[rowIndex].replaceRange(columnIndex, columnIndex + 1, "#")
            space[rowIndex] = updatedRow
            val w = space[0].length
            val h = space.size

            var currentPosition: Pair<Pair<Int, Int>, Char> = Pair(Pair(0, 0), ' ')
            for (h1 in 0 until h) {
                for (w1 in 0 until w) {
                    if (space[h1][w1] == '^' || space[h1][w1] == 'v' || space[h1][w1] == '<' || space[h1][w1] == '>') {
                        currentPosition = Pair(Pair(w1, h1), space[h1][w1])
                    }
                }
            }
            val positionsWhereBeen = mutableSetOf<Pair<Pair<Int, Int>, Char>>()
            positionsWhereBeen.add(currentPosition)
            var isTimeParadox = false
            while (true) {
                val (position, direction) = currentPosition
                val (x, y) = position
                val (dx, dy) = getDxy(direction)
                if (x + dx < 0 || x + dx >= w || y + dy < 0 || y + dy >= h) {
                    break
                }
                if (space[y + dy][x + dx] == '#') {
                    val newDirection = rotateDirection(direction)
                    // println(newDirection)
                    currentPosition = Pair(position, newDirection)
                    continue
                }

                val newPosition = Pair(Pair(x + dx, y + dy), direction)

                if (positionsWhereBeen.contains(newPosition)) {
                    isTimeParadox = true
                    break
                }

                positionsWhereBeen.add(newPosition)
                // println(newPosition)
                currentPosition = newPosition
            }

            isTimeParadox
        }.count()
    }

    val input = readInput("day06_1")
    // part1(input).println()
    part2(input).println()
}
