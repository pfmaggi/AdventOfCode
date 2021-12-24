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

package com.pietromaggi.aoc2021.day20

import com.pietromaggi.aoc2021.readInput

var conversion = ""

fun evolve(previous: List<CharArray>, even: Boolean) : List<CharArray> {
    var image = listOf<CharArray>()
    val background = if (conversion[0] == '#') if (even) '#' else '.' else '.'
    fun compute(i: Int, j: Int): Char {
        fun convert(line: List<Char>): Char {
            val index = line.map { if (it == '.') '0' else '1' }.map(Char::digitToInt).joinToString("").toInt(radix = 2)
            return conversion[index]
        }

        val list = mutableListOf<Char>()
        for (idx1 in -1..1) for (idx2 in -1..1)
            list += image.getOrNull(i+idx1)?.getOrNull(j+idx2) ?: background

        return convert(list)
    }

    fun expand(oldArray: List<CharArray>, oldSize: Int) : List<CharArray> {

        return buildList {
            val empty = CharArray(oldSize+2) { background }
            add(empty)
            oldArray.forEach {
                add(charArrayOf(background) + it + charArrayOf(background))
            }
            add(empty)
        }
    }

    image = expand(previous, previous.size)
    val temp = buildList {
        image.forEach { line ->
            add(line.toList().joinToString("").toCharArray())
        }
    }
    for (i in image.indices) {
        for (j in image.indices) {
            temp[i][j] = compute(i, j)
        }
    }
    return temp
}

fun part1(input: List<String>): Int {
    conversion = input[0]
    var inputMap = input.drop(2)
        .takeWhile(String::isNotEmpty)
        .map(String::toCharArray)

    inputMap = evolve(inputMap, false)
    inputMap = evolve(inputMap, true)

    return inputMap.sumOf { line -> line.count { char -> char == '#' } }
}

fun part2(input: List<String>, iteration: Int): Int {
    conversion = input[0]
    var inputMap = input.drop(2)
        .takeWhile(String::isNotEmpty)
        .map(String::toCharArray)

    repeat(iteration) { count ->
        inputMap = evolve(inputMap, (1 == count % 2))
    }

    return inputMap.sumOf { line -> line.count { char -> char == '#' } }
}

fun main() {
    val input = readInput("Day20")
    println("""
    AoC2021 - Day20 - Kotlin
    ========================
    How many pixels are lit in the resulting image?
    --> ${part2(input, 2)}
    How many pixels are lit in the resulting image?
    --> ${part2(input, 50)}
    """)
}
