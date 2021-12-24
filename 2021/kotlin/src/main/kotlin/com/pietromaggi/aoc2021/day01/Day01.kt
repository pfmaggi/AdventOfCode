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

package com.pietromaggi.aoc2021.day01

import com.pietromaggi.aoc2021.readInput

/*
 * Count number of increases in the List<Int> passed to the function.
 */
fun countIncreases(numbers: List<Int>): Int {
    return numbers.zipWithNext().count  { (first, second) -> second > first }
}

fun part1(input: List<String>) =
    countIncreases(input.map(String::toInt))

fun part2(input: List<String>) =
    countIncreases(input.map(String::toInt).windowed(3) { it.sum() })

fun main() {
    val input = readInput("Day01")
    println("How many measurements are larger than the previous measurement?")
    println("My puzzle answer is: ${part1(input)}")
    println("How many sums are larger than the previous sum?")
    println("My puzzle answer is: ${part2(input)}")
}
