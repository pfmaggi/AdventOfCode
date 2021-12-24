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

package com.pietromaggi.aoc2021.day10

import com.pietromaggi.aoc2021.readInput

val opening = setOf('(', '[', '{', '<')
val closing = setOf(')', ']', '}', '>')
val mapping = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')

fun part1(input: List<String>): Int {
    val points = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137
    )
    val parens = mutableListOf<Char>()
    var result = 0
    checkLine@
    for (line in input) {
        parens.clear()
        for (paren in line) {
            if (opening.contains(paren)) parens.add(paren)
            if (closing.contains(paren)) {
                if (mapping[paren] != parens.last()) {
                    result += points[paren] as Int
                    continue@checkLine
                }
                parens.removeLast()
            }
        }
    }
    return result
}

fun part2(input: List<String>): Long {
    val points = mapOf(
        '(' to 1,
        '[' to 2,
        '{' to 3,
        '<' to 4
    )
    val parens = mutableListOf<Char>()
    val resultList = mutableListOf<Long>()
    checkLine@
    for (line in input) {
        parens.clear()
        for (paren in line) {
            if (opening.contains(paren)) parens.add(paren)
            if (closing.contains(paren)) {
                if (mapping[paren] != parens.last()) {
                    continue@checkLine
                }
                parens.removeLast()
            }
        }
        resultList.add(parens.reversed().fold(0) {sum, paren -> sum * 5 + points[paren] as Int})
    }
    resultList.sort()
    return resultList[resultList.size / 2]

}

fun main() {
    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
