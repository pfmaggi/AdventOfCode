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

package com.pietromaggi.aoc2021.day17

import com.pietromaggi.aoc2021.readInput
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int)
data class Area(val topLeft: Point, val bottomRight: Point)
data class Velocities(val x: Int, val y: Int)

fun part1(input: Area) = (1 until input.bottomRight.y.absoluteValue).sum()

fun check(start: Velocities, target: Area) : Pair<Boolean, Int> {
    var position = Point(0, 0)
    var currentVelocities = start
    var result = false
    var max = 0
    while (!result && (position.x < target.bottomRight.x) && (position.y > target.bottomRight.y)) {
        max = position.y.coerceAtLeast(max)
        position = Point(position.x + currentVelocities.x, position.y + currentVelocities.y)
        currentVelocities = Velocities((currentVelocities.x - 1).coerceAtLeast(0), (currentVelocities.y - 1))
        result = ((target.topLeft.x <= position.x) && (position.x <= target.bottomRight.x) &&
                ((target.bottomRight.y <= position.y) && (position.y <= target.topLeft.y)))
    }
    return Pair(result, max)
}

fun part2(input: Area): List<Int> {
    val results = mutableListOf<Int>()
    for (xRange in 0..input.bottomRight.x) {
        for (yRange in input.bottomRight.y..(-input.bottomRight.y)) {
            val(inTarget, max) = check(Velocities(xRange, yRange), input)
            if (inTarget) {
                results.add(max)
            }
        }
    }
    return results
}

fun parseArea(input: List<String>) : Area {
    val (x, y) = input[0].removePrefix("target area: ").split(", ")
    val (x1, x2) = x.removePrefix("x=").split("..").map(String::toInt)
    val (y2, y1) = y.removePrefix("y=").split("..").map(String::toInt)

    return Area(Point(x1, y1), Point(x2, y2))
}

fun main() {
    val input = readInput("Day17")
    val area = parseArea(input)
    println(part1(area))
//    println(part2(area).maxOrNull())
    println(part2(area).count())
}
