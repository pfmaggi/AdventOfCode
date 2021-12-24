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

package com.pietromaggi.aoc2021.day02

import com.pietromaggi.aoc2021.readInput
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


internal class Day02KtTest {
    private val testInput: List<String>
        get() {
            return readInput("Day02_test")
        }

    @Test
    fun part1() {
        Assertions.assertEquals(150, part1(testInput))
    }

    @Test
    fun part2() {
        Assertions.assertEquals(900, part2(testInput) )
    }
}