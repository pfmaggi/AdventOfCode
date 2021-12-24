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

package com.pietromaggi.aoc2021.day07

import com.pietromaggi.aoc2021.readInput
import kotlin.math.abs

fun part1(input: List<String>): Int {
    val crabList = input[0].split(",").map(String::toInt).sorted()

    // We use the median
    val median = crabList[crabList.size / 2]
    val resultList = mutableListOf<Int>()
    for (candidate in (median - 1)..(median + 1)) {
        resultList.add(crabList.fold(0) { sum, element -> sum + abs(element - candidate) })
    }
    return resultList.reduce { a: Int, b: Int -> a.coerceAtMost(b) }
}

fun movesToFuel(moves: Int) = (1..moves).sum()

fun part2(input: List<String>): Int {
    val crabList = input[0].split(",").map(String::toInt).sorted()

    // We use the average
    val average = (0.5 + crabList.average()).toInt()
    val resultList = mutableListOf<Int>()
    for (candidate in (average - 1)..(average + 1)) {
        resultList.add(crabList.fold(0) { sum, element -> sum + movesToFuel(abs(element - candidate)) })
    }
    return resultList.reduce { a: Int, b: Int -> a.coerceAtMost(b) }
}

fun main() {
    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
