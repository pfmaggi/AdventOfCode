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

package com.pietromaggi.aoc2021.day03

import com.pietromaggi.aoc2021.readInput
import java.lang.Integer.parseInt

data class DigitsCount(var zeros: Int, var ones: Int)

fun digitsCounts(input: List<String>): MutableList<DigitsCount> {
    val counts: MutableList<DigitsCount> = mutableListOf()

    input.forEach { line ->
        line.forEachIndexed() { idx, digit ->
            if (counts.size <= idx) counts.add(idx, DigitsCount(0, 0))
            if ('0' == digit) {
                counts[idx].zeros++
            } else counts[idx].ones++
        }
    }
    return counts
}

fun part1(input: List<String>): Int {
    var gamma = ""
    var epsilon = ""
    val counts: MutableList<DigitsCount> = digitsCounts(input)
    counts.iterator().forEach { digit ->
        if (digit.zeros > digit.ones) {
            gamma += "0"
            epsilon += "1"
        } else {
            gamma += "1"
            epsilon += "0"
        }
    }

    return parseInt(gamma, 2) * parseInt(epsilon, 2)
}

fun part2(input: List<String>): Int {
    val co2Scrubber: Int
    val oxygenGenerator : Int
    var counts: MutableList<DigitsCount> = digitsCounts(input)
    var numbers: MutableList<String> = input.toMutableList()
    while (numbers.size > 1) {
        counts.forEachIndexed { idx, _ ->
            counts = digitsCounts(numbers)
            for (line_idx in numbers.lastIndex downTo 0) {
                val line = numbers[line_idx]

                if (counts[idx].zeros > counts[idx].ones) {
                    if ('1' == line[idx]) {
                        numbers.removeAt(line_idx)
                    }
                } else {
                    if ('0' == line[idx]) {
                        numbers.removeAt(line_idx)
                    }
                }
            }
            if (numbers.size == 1) return@forEachIndexed
        }

    }
    oxygenGenerator = parseInt(numbers[0], 2)

    counts = digitsCounts(input)
    numbers = input.toMutableList()

    while (numbers.size > 1) {
        counts.forEachIndexed { idx, _ ->
            if (numbers.size == 1) return@forEachIndexed
            counts = digitsCounts(numbers)
            for (line_idx in numbers.lastIndex downTo 0) {
                val line = numbers[line_idx]

                if (counts[idx].zeros > counts[idx].ones) {
                    if ('0' == line[idx]) {
                        numbers.removeAt(line_idx)
                    }
                } else {
                    if ('1' == line[idx]) {
                        numbers.removeAt(line_idx)
                    }
                }
            }
        }
    }

    co2Scrubber = parseInt(numbers[0], 2)
    return oxygenGenerator * co2Scrubber
}

fun main() {
    val input = readInput("Day03")
    println("What is the power consumption of the submarine?")
    println("My puzzle answer is: ${part1(input)}")
    println("What is the life support rating of the submarine?")
    println("My puzzle answer is: ${part2(input)}")
}
