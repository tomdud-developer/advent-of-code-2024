package com.xtb

import Day12Java

fun main() {
    fun part1(input: List<String>): Int {
        class RegionData(
            var points: List<Pair<Int, Int>>,
            var perimeter: Int,
            var area: Int,
        )

        val directionsToSearch = arrayOf(Pair(0, 1), Pair(-1, 0), Pair(1, 0), Pair(0, -1))
        val charArray = input.map { it.toCharArray() }.toTypedArray()
        val perimeters = mutableMapOf<Char, Int>()
        val areas = mutableMapOf<Char, Int>()
        val regions = mutableMapOf<Char, List<RegionData>>()
        for (x in charArray.indices) {
            for (y in charArray[x].indices) {
                val vegetable = charArray[x][y]
                println(vegetable)
                areas[vegetable] = areas.getOrDefault(charArray[x][y], 0) + 1

                val regions2 = regions.getOrPut(vegetable) { mutableListOf() }
        /*        regions2 = regions2 + RegionData(listOf(Pair(x,y)), 0, 0)
                var region : RegionData =
                    (directionsToSearch.map { direction ->
                    val x1 = x + direction.first
                    val y1 = y + direction.second
                    regions.firstOrNull { it.points.contains(Pair(x1,y1)) }
                }.firstOrNull { it != null } ) ?:

                val region = optionalRegion?: RegionData(listOf(Pair(x,y)), 0, 0)

*/
                directionsToSearch.forEach { direction ->
                    val x1 = x + direction.first
                    val y1 = y + direction.second

                    val isInBoundary = x1 >= 0 && x1 < charArray.size && y1 >= 0 && y1 < charArray[x1].size

                    if (isInBoundary) {
                        if (charArray[x1][y1] == charArray[x][y]) {
                            println("found the same not add perimeter")
                        } else {
                            println("found different, add perimeter")
                            perimeters[charArray[x][y]] = perimeters.getOrDefault(charArray[x][y], 0) + 1
                        }
                    } else {
                        println("out of boundary, add perimeter")
                        perimeters[charArray[x][y]] = perimeters.getOrDefault(charArray[x][y], 0) + 1
                    }
                }
            }
        }

        println("perimeters:" + perimeters)
        println("areas:" + areas)

        return areas.map { it.key to it.value }.sumOf { (perimeters[it.first] ?: 0) * it.second }
    }

    val input = readInput("day12_1")
    // part1(input).println()
    Day12Java().part12(input, true).println()
}
