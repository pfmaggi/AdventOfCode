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

package com.pietromaggi.aoc2021.day11

import com.pietromaggi.aoc2021.readInput

fun part1(input: List<String>, iterations: Int): Int {
    data class Cell(val value: Int, val flashed: Boolean)

    val numbersMap = mutableMapOf<Pair<Int, Int>, Cell>()
    val sizeX = input[0].length - 1
    val sizeY = input.size - 1
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            numbersMap[Pair(x, y)] = Cell(char.code - 48, false)
        }
    }
    var result = 0

    repeat(
        iterations,
    ) {
        for ((key, cell) in numbersMap) {
            numbersMap[key] = Cell(cell.value + 1, false)
        }
        var thereIsStillWorkToDo = true
        while (thereIsStillWorkToDo) {
            thereIsStillWorkToDo = false
            // Now we ckeck for for values>9
            for ((key, cell) in numbersMap) {
                val (x, y) = key
                if (cell.value > 9) {
                    result++
                    thereIsStillWorkToDo = true
                    numbersMap[key] = Cell(0, true)
                    if (x > 0) {
                        if (y > 0) {
                            val currentCell = numbersMap[Pair(x - 1, y - 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x - 1, y - 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y < sizeY) {
                            val currentCell = numbersMap[Pair(x - 1, y + 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x - 1, y + 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        val currentCell = numbersMap[Pair(x - 1, y)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x - 1, y)] =
                            Cell(currentCell.value + 1, false)
                    }
                    if (x < sizeX) {
                        if (y > 0) {
                            val currentCell = numbersMap[Pair(x + 1, y - 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x + 1, y - 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y < sizeY) {
                            val currentCell = numbersMap[Pair(x + 1, y + 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x + 1, y + 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        val currentCell = numbersMap[Pair(x + 1, y)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x + 1, y)] =
                            Cell(currentCell.value + 1, false)
                    }
                    if (y > 0) {
                        val currentCell = numbersMap[Pair(x, y - 1)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x, y - 1)] =
                            Cell(currentCell.value + 1, false)
                    }
                    if (y < sizeY) {
                        val currentCell = numbersMap[Pair(x, y + 1)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x, y + 1)] =
                            Cell(currentCell.value + 1, false)
                    }
                }
            }
        }
    }

    return result
}

fun part2(input: List<String>): Int {
    data class Cell(val value: Int, val flashed: Boolean)

    val numbersMap = mutableMapOf<Pair<Int, Int>, Cell>()
    val sizeX = input[0].length - 1
    val sizeY = input.size - 1
    input.forEachIndexed { y, line ->
        line.forEachIndexed { x, char ->
            numbersMap[Pair(x, y)] = Cell(char.code - 48, false)
        }
    }
    var result = 0
    var count = 0

    while (count < 100) {
        count = 0
        result++

        for ((key, cell) in numbersMap) {
            numbersMap[key] = Cell(cell.value + 1, false)
        }
        var thereIsStillWorkToDo = true
        while (thereIsStillWorkToDo) {
            thereIsStillWorkToDo = false
            // Now we ckeck for for values>9
            for ((key, cell) in numbersMap) {
                val (x, y) = key
                if (cell.value > 9) {
                    count++
                    thereIsStillWorkToDo = true
                    numbersMap[key] = Cell(0, true)
                    if (x > 0) {
                        if (y > 0) {
                            val currentCell = numbersMap[Pair(x - 1, y - 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x - 1, y - 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y < sizeY) {
                            val currentCell = numbersMap[Pair(x - 1, y + 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x - 1, y + 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        val currentCell = numbersMap[Pair(x - 1, y)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x - 1, y)] =
                            Cell(currentCell.value + 1, false)
                    }
                    if (x < sizeX) {
                        if (y > 0) {
                            val currentCell = numbersMap[Pair(x + 1, y - 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x + 1, y - 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        if (y < sizeY) {
                            val currentCell = numbersMap[Pair(x + 1, y + 1)] as Cell
                            if (!currentCell.flashed) numbersMap[Pair(x + 1, y + 1)] =
                                Cell(currentCell.value + 1, false)
                        }
                        val currentCell = numbersMap[Pair(x + 1, y)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x + 1, y)] =
                            Cell(currentCell.value + 1, false)
                    }
                    if (y > 0) {
                        val currentCell = numbersMap[Pair(x, y - 1)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x, y - 1)] =
                            Cell(currentCell.value + 1, false)
                    }
                    if (y < sizeY) {
                        val currentCell = numbersMap[Pair(x, y + 1)] as Cell
                        if (!currentCell.flashed) numbersMap[Pair(x, y + 1)] =
                            Cell(currentCell.value + 1, false)
                    }
                }
            }
        }
    }

    return result
}

fun main() {
    val input = readInput("Day11")
    println(part1(input, 100))
    println(part2(input))
}
