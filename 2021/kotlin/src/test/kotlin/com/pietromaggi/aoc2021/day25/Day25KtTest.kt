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

package com.pietromaggi.aoc2021.day25

import com.pietromaggi.aoc2021.readInput
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class Day25KtTest {
    private val testInput: List<String>
        get() {
            return readInput("Day25_test")
        }

    @Test
    fun part1() {
        assertEquals(58, part1(testInput))
    }
}