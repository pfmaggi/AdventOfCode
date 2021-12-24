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

package com.pietromaggi.aoc2021.day13

import com.pietromaggi.aoc2021.readInput

data class Dot(val x: Int, val y:Int)
data class FoldInfo(val type: Char, val cord: Int)

fun parseInput(input: List<String>): Pair<MutableSet<Dot>, MutableList<FoldInfo>> {
    val dots = mutableSetOf<Dot>()
    val folds = mutableListOf<FoldInfo>()
    var readDotsCoords = true
    for (line in input) {
        if (line.isBlank()) readDotsCoords = false else {
            if (readDotsCoords) {
                val (x, y) = line.split(",").map(String::toInt)
                dots.add(Dot(x, y))
            } else {
                val (axes, value) = line.split("=")

                folds.add(FoldInfo(axes.trimEnd().last(), value.toInt()))
            }
        }
    }
    return Pair(dots, folds)
}

fun doFold(foldInfo: FoldInfo, dots: Set<Dot>): Set<Dot> {
    return if (foldInfo.type == 'x') {
        // fold is vertical
        buildSet {
            for (dot in dots) {
                this.add(
                    Dot(
                        (
                                if (dot.x > foldInfo.cord) {
                                    foldInfo.cord * 2 - dot.x
                                } else dot.x), dot.y
                    )
                )
            }
        }
    } else {
        // fold is horizontal
        buildSet {
            for (dot in dots) {
                this.add(
                    Dot(
                        dot.x,
                        if (dot.y > foldInfo.cord) {
                            foldInfo.cord * 2 - dot.y
                        } else dot.y
                    )
                )
            }
        }
    }
}

fun buildCode(dots: Set<Dot>) : List<String> {
    val sizeX = dots.maxOf { it.x }
    val sizeY = dots.maxOf { it.y }
    return buildList {
        for (y in 0..sizeY) {
            var line = ""
            for (x in 0..sizeX) {
                line += if (dots.contains(Dot(x, y))) "#" else " "
            }
            this.add(line)
        }
    }

}

fun part1(input: List<String>): Int {
    val (dots, folds) = parseInput(input)

    // evaluate just the first fold
    return doFold(folds[0], dots).size
}

fun part2(input: List<String>): List<String> {
    val (dots, folds) = parseInput(input)

    var foldedDots = dots as Set<Dot>
    folds.forEach { foldInfo ->
        foldedDots = doFold(foldInfo, foldedDots)
    }

    return buildCode(foldedDots)
}

fun main() {
    val input = readInput("Day13")
    println(part1(input))
    for (line in part2(input)) {
        println(line)
    }
}
