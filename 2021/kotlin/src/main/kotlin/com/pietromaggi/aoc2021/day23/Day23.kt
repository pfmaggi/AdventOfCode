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

package com.pietromaggi.aoc2021.day23

import com.pietromaggi.aoc2021.readInput

fun part1(input: List<String>) : Int {
    return 10526
}

fun part2(input: List<String>) : Int {
    return 41284
}

fun main() {
    val input = readInput("Day23")
    println("""    
    ### DAY 23 ###
    ==============
    --> Solved by hand
    What is the least energy required to organize the amphipods?
    --> ${part1(input)}
    What is the least energy required to organize the amphipods?
    --> ${part2(input)}
    """)
}
