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

package com.pietromaggi.aoc2021.day25

import com.pietromaggi.aoc2021.readInput

data class Point(val x: Int, val y: Int)

fun parse(input: List<String>): Map<Point, Char> {
    val n = input.size
    val m = input[0].length
    val status = buildMap {
        for (i in 0 until n) for (j in 0 until m) {
            val c = input[i][j]
            if ((c == '>') or (c == 'v')) {
                this[Point(i, j)] = c
            }
        }
    }

    return status
}

fun doStep(status: Map<Point, Char>, n: Int, m: Int): Pair<Map<Point, Char>, Boolean> {
    var updated = false

    val newStatus = mutableMapOf<Point, Char>()
    for ((point, c) in status) {
        var (x, y) = point
        if (c == '>') {
            y = (point.y + 1) % m
        }

        val newPoint = Point(x, y)
        if (status.containsKey(newPoint)) {
            newStatus[point] = c
        } else {
            newStatus[newPoint] = c
            updated = true
        }
    }

    val finalStatus = mutableMapOf<Point, Char>()
    for ((point, c) in newStatus) {
        var (x, y) = point
        if (c == 'v') {
            x = (point.x + 1) % n
        }
        val newPoint = Point(x, y)
        if (newStatus.containsKey(newPoint)) {
            finalStatus[point] = c
        } else {
            finalStatus[newPoint] = c
            updated = true
        }
    }

    return Pair(finalStatus, updated)
}

fun part1(input: List<String>): Int {
    var status = parse(input)
    val n = input.size
    val m = input[0].length
    var count = 0
    var updated = true
    while (updated) {
        count++
        doStep(status, n, m).let {
            status = it.first
            updated = it.second
        }
    }
    return count
}

fun main() {
    val input = readInput("Day25")
    println("""
    ### DAY 25 ###
    ==============
    What is the first step on which no sea cucumbers move?
    --> ${part1(input)}
    """)
}