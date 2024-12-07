package com.xtb

fun main() {
    fun part1(input: List<String>): Int {
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
            val (dx, dy) =
                when (direction) {
                    '^' -> Pair(0, -1)
                    'v' -> Pair(0, 1)
                    '<' -> Pair(-1, 0)
                    '>' -> Pair(1, 0)
                    else -> Pair(0, 0)
                }
            if (x + dx < 0 || x + dx >= w || y + dy < 0 || y + dy >= h) {
                break
            }
            if (input[y + dy][x + dx] == '#') {
                val newDirection =
                    when (direction) {
                        '^' -> '>'
                        'v' -> '<'
                        '<' -> '^'
                        '>' -> 'v'
                        else -> throw IllegalArgumentException("Invalid direction")
                    }
                println(newDirection)
                currentPosition = Pair(position, newDirection)
                continue
            }

            val newPosition = Pair(Pair(x + dx, y + dy), direction)
            positionsWhereBeen.add(newPosition.first)
            println(newPosition)
            currentPosition = newPosition
        }

        return positionsWhereBeen.size
    }

    fun part2(input: List<String>): Int {
        val w = input[0].length
        val h = input.size

        fun rotateDirection(direction: Char) =
            when (direction) {
                '^' -> '>'
                'v' -> '<'
                '<' -> '^'
                '>' -> 'v'
                else -> throw IllegalArgumentException("Invalid direction")
            }

        var currentPosition: Pair<Pair<Int, Int>, Char> = Pair(Pair(0, 0), ' ')
        for (h1 in 0 until h) {
            for (w1 in 0 until w) {
                if (input[h1][w1] == '^' || input[h1][w1] == 'v' || input[h1][w1] == '<' || input[h1][w1] == '>') {
                    currentPosition = Pair(Pair(w1, h1), input[h1][w1])
                }
            }
        }
        println(currentPosition)
        val positionsOfNewObstacles = mutableSetOf<Pair<Int, Int>>()
        val positionsWhereBeen = mutableListOf<Pair<Pair<Int, Int>, Char>>()
        positionsWhereBeen.add(currentPosition)
        while (true) {
            val (position, direction) = currentPosition
            val (x, y) = position
            val (dx, dy) =
                when (direction) {
                    '^' -> Pair(0, -1)
                    'v' -> Pair(0, 1)
                    '<' -> Pair(-1, 0)
                    '>' -> Pair(1, 0)
                    else -> Pair(0, 0)
                }
            if (x + dx < 0 || x + dx >= w || y + dy < 0 || y + dy >= h) {
                val newDirection = rotateDirection(direction)
                println(newDirection)
                currentPosition = Pair(position, newDirection)
                continue
            }
            if (input[y + dy][x + dx] == '#') {
                val newDirection = rotateDirection(direction)
                println(newDirection)
                currentPosition = Pair(position, newDirection)
                continue
            }

            val newPosition = Pair(Pair(x + dx, y + dy), direction)

            val possibleClosingPositions = positionsWhereBeen.filter { it.first == newPosition.first }
            possibleClosingPositions.map {
                if (rotateDirection(direction) == it.second) {
                    println("Found closing position")
                    val (dxx, dyy) =
                        when (direction) {
                            '^' -> Pair(0, -1)
                            'v' -> Pair(0, 1)
                            '<' -> Pair(-1, 0)
                            '>' -> Pair(1, 0)
                            else -> Pair(0, 0)
                        }
                    val obstacle = (newPosition.first.first + dxx) to (newPosition.first.second + dyy)
                    positionsOfNewObstacles.add(obstacle)
                }
            }

            positionsWhereBeen.add(newPosition)
            println(newPosition)
            currentPosition = newPosition

            println("obstaclesCount: " + positionsOfNewObstacles + " " + positionsOfNewObstacles.size)
            // Thread.sleep(50)

            input.forEach(
                { line ->
                    println(
                        line.mapIndexed { index, c ->
                            if (positionsOfNewObstacles.contains(Pair(index, input.indexOf(line)))) {
                                'O'
                            } else if (positionsWhereBeen.filter {
                                        pos ->
                                    pos.first.first == index && pos.first.second == input.indexOf(line)
                                }.isNotEmpty()
                            ) {
                                '*'
                            } else {
                                c
                            }
                        }.joinToString(""),
                    )
                },
            )
        }

        return positionsWhereBeen.size
    }

    val input = readInput("day06_1")
    // part1(input).println()
    part2(input).println()
}
