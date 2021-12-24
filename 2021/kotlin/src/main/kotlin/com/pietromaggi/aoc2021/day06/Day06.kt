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

package com.pietromaggi.aoc2021.day06

import com.pietromaggi.aoc2021.readInput

fun compute(input: List<String>, days: Int): Long {
    var fishArray = LongArray(9)

    input[0].split(",").map(String::toInt).map {fish ->
        fishArray[fish] = fishArray[fish] + 1}

    repeat(days) {
        val newGeneration = LongArray(9)
        for (idx in fishArray.indices) {
            if (idx == 0) {
                newGeneration[8] = fishArray[idx]
                newGeneration[6] = newGeneration[6] + fishArray[idx]
            } else {
                newGeneration[idx - 1] = newGeneration[idx - 1] + fishArray[idx]
            }
        }
        fishArray = newGeneration
    }
    return fishArray.sum()
}

fun main() {
    val input = readInput("Day06")
    println(compute(input, 80))
    println(compute(input, 256))
}
