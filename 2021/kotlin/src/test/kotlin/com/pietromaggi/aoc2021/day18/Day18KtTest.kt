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

package com.pietromaggi.aoc2021.day18

import com.pietromaggi.aoc2021.readInput
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertEquals

internal class Day18KtTest {
    private val numbers: List<SnailfishNumber>
        get() {
            return readInput("Day18_test").map(::parseInput)
        }

    @Test
    fun part1() {
        assertEquals(143, part1(listOf("[[1,2],[[3,4],5]]").map(::parseInput)))
        assertEquals(1384, part1(listOf("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]").map(::parseInput)))
        assertEquals(445, part1(listOf("[[[[1,1],[2,2]],[3,3]],[4,4]]").map(::parseInput)))
        assertEquals(3488, part1(listOf("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]").map(::parseInput)))
        assertEquals(4140, part1(numbers))
    }

    @Test
    fun part2() {
        assertEquals(3993, part2(numbers))
    }
}
