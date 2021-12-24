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

package com.pietromaggi.aoc2021.day16

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

internal class Day16KtTest {

    @Test
    fun part1() {
        assertEquals(6, part1(listOf("D2FE28")))
        assertEquals(9, part1(listOf("38006F45291200")))
        assertEquals(14, part1(listOf("EE00D40C823060")))
        assertEquals(16, part1(listOf("8A004A801A8002F478")))
        assertEquals(12, part1(listOf("620080001611562C8802118E34")))
        assertEquals(23, part1(listOf("C0015000016115A2E0802F182340")))
        assertEquals(31, part1(listOf("A0016C880162017C3686B18A3D4780")))
    }

    @Test
    fun part2() {
        assertEquals(2021L, part2(listOf("D2FE28")))
        assertEquals(3L, part2(listOf("C200B40A82")))
        assertEquals(54L, part2(listOf("04005AC33890")))
        assertEquals(7L, part2(listOf("880086C3E88112")))
        assertEquals(9L, part2(listOf("CE00C43D881120")))
        assertEquals(1L, part2(listOf("D8005AC2A8F0")))
        assertEquals(0L, part2(listOf("F600BC2D8F")))
        assertEquals(0L, part2(listOf("9C005AC2F8F0")))
        assertEquals(1L, part2(listOf("9C0141080250320F1802104A08")))
    }
}
