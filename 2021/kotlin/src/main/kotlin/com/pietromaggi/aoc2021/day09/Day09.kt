/*
 * Copyright 2021 Pietro F. Maggi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pietromaggi.aoc2021.day09

import com.pietromaggi.aoc2021.readInput

fun part1(input: List<String>): Int {
    val numbersMap = mutableMapOf<Pair<Int, Int>, Int>()
    val sizeX = input[0].length - 1
    val sizeY = input.size - 1
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            numbersMap[Pair(x, y)] = char.digitToInt()
        }
    }

    var result = 0
    checkMap@
    for ((key, value) in numbersMap) {
        val (x,y) = key
        if (x > 0) if (value >= numbersMap[Pair(x - 1, y)] as Int) continue@checkMap
        if (x < sizeX) if (value >= numbersMap[Pair(x + 1, y)] as Int) continue@checkMap
        if (y > 0) if (value >= numbersMap[Pair(x, y - 1)] as Int) continue@checkMap
        if (y < sizeY) if (value >= numbersMap[Pair(x, y + 1)] as Int) continue@checkMap
        result += value + 1
    }

    return result
}

fun part2(input: List<String>): Int {
    data class Cell(val value: Int, val counted: Boolean)

    val numbersMap = mutableMapOf<Pair<Int, Int>, Cell>()
    val sizeX = input[0].length - 1
    val sizeY = input.size - 1
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            numbersMap[Pair(x, y)] = Cell(char.digitToInt(), false)
        }
    }
    val result = mutableListOf<Int>()

    fun countIt(x: Int, y: Int, cell: Cell): Int {
        if ((cell.value == 9) or (cell.counted)) return 0
        var partialCount = 1
        numbersMap[Pair(x, y)] = Cell(cell.value, true)
        if (x > 0) partialCount += countIt(x - 1, y, numbersMap[Pair(x - 1, y)]!!)
        if (x < sizeX) partialCount += countIt(x + 1, y, numbersMap[Pair(x + 1, y)]!!)
        if (y > 0) partialCount += countIt(x, y - 1, numbersMap[Pair(x, y - 1)]!!)
        if (y < sizeY) partialCount += countIt(x, y + 1, numbersMap[Pair(x, y + 1)]!!)

        return partialCount
    }

    checkMap@
    for ((key, cell) in numbersMap) {
        if ((cell.value == 9) or (cell.counted)) continue@checkMap
        val (x, y) = key
        result.add(countIt(x, y, cell))
    }
    result.sortDescending()
    return result.take(3).fold(1) { sum, element -> sum * element }
}

fun main() {
    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
