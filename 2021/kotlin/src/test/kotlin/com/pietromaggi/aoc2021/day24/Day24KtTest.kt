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

package com.pietromaggi.aoc2021.day24

import com.pietromaggi.aoc2021.readInput
import org.junit.jupiter.api.Test
import kotlin.time.*
import org.junit.jupiter.api.Assertions.assertEquals

@OptIn(ExperimentalTime::class)
internal class Day24KtTest {
    @Test
    fun toReg() {
        assertEquals(0, "w".toReg())
        assertEquals(1, "x".toReg())
        assertEquals(2, "y".toReg())
        assertEquals(3, "z".toReg())
        assertEquals(4, "".toReg())
    }

    @Test
    fun decrement() {
        assertEquals("12345678991234", "12345678991235".decrement())
        assertEquals("12345678989999", "12345678991111".decrement())
        assertEquals("12345678912221", "12345678912222".decrement())
    }

    @Test
    fun parse() {
        val instructions = listOf("inp z", "inp x", "mul z 3", "eql z x")
        val program = buildList<Instruction> {
            this.add(Instruction(Op.INP, 3, 5, 0))
            this.add(Instruction(Op.INP, 1, 5, 0))
            this.add(Instruction(Op.MUL_NUM, 3, 4, 3))
            this.add(Instruction(Op.EQL, 3, 1, 0))
        }
        assertEquals(program, parse(instructions))
    }

    @Test
    fun compute() {
        var program = parse(listOf("inp z", "inp x", "mul z 3", "eql z x"))
        assertEquals(0, compute(program, "12345678991234"))
        assertEquals(1, compute(program, "13345678991234"))
        program = parse(listOf("inp z", "inp x", "add z 3", "eql z x"))
        assertEquals(0, compute(program, "13345678991234"))
        assertEquals(1, compute(program, "14345678991234"))
        program = parse(listOf("inp z", "inp x", "div z 3", "eql z x"))
        assertEquals(0, compute(program, "92345678991234"))
        assertEquals(1, compute(program, "82345678991234"))
        program = parse(listOf("inp z", "inp x", "mod z 3", "eql z x"))
        assertEquals(0, compute(program, "23345678991234"))
        assertEquals(1, compute(program, "22345678991234"))
    }

    @Test
    fun reduced() {
        var number = "999999"
        var start = TimeSource.Monotonic.markNow()
        val original = buildList<Int> {
            repeat(100000) {
                this.add(compute(parse(readInput("Day24_orig")), number + "99999999"))
                number = number.decrement()
            }
        }

        println("Original file in ${start.elapsedNow()}")
        start = TimeSource.Monotonic.markNow()

        number = "999999"
        val reduced = buildList<Int> {
            repeat(100000) {
                this.add(compute(parse(readInput("Day24_mod")), number + "99999999"))
                number = number.decrement()
            }
        }
        println("Modded file in ${start.elapsedNow()}")

        assertEquals(original, reduced)

        assertEquals(compute(parse(readInput("Day24_orig")), "11111111111111"), compute(parse(readInput("Day24_reduced")), "11111111111111"))
        assertEquals(compute(parse(readInput("Day24_orig")), "22222222222222"), compute(parse(readInput("Day24_reduced")), "22222222222222"))
        assertEquals(compute(parse(readInput("Day24_orig")), "33333333333333"), compute(parse(readInput("Day24_reduced")), "33333333333333"))
        assertEquals(compute(parse(readInput("Day24_orig")), "44444444444444"), compute(parse(readInput("Day24_reduced")), "44444444444444"))
        assertEquals(compute(parse(readInput("Day24_orig")), "55555555555555"), compute(parse(readInput("Day24_reduced")), "55555555555555"))
        assertEquals(compute(parse(readInput("Day24_orig")), "66666666666666"), compute(parse(readInput("Day24_reduced")), "66666666666666"))
        assertEquals(compute(parse(readInput("Day24_orig")), "77777777777777"), compute(parse(readInput("Day24_reduced")), "77777777777777"))
        assertEquals(compute(parse(readInput("Day24_orig")), "88888888888888"), compute(parse(readInput("Day24_reduced")), "88888888888888"))
        assertEquals(compute(parse(readInput("Day24_orig")), "99999999999999"), compute(parse(readInput("Day24_reduced")), "99999999999999"))
    }

    @Test
    fun part1() {
        assertEquals(0, compute(parse(readInput("Day24_orig")), "99893999291967"))
        assertEquals(0, compute(parse(readInput("Day24_reduced")), "99893999291967"))
        assertEquals(0, compute(parse(readInput("Day24_mod")), "99893999291967"))
        assertEquals(0, computeDirect("99893999291967"))
        assertEquals(0, checkValue(99893999291967))
    }

    @Test
    fun part2() {
        assertEquals(0, compute(parse(readInput("Day24_orig")), "34171911181211"))
        assertEquals(0, compute(parse(readInput("Day24_reduced")), "34171911181211"))
        assertEquals(0, compute(parse(readInput("Day24_mod")), "34171911181211"))
        assertEquals(0, computeDirect("34171911181211"))
        assertEquals(0, checkValue(34171911181211))
    }

    @Test
    fun part2_fast() {
        var number = "999999"
        var start = TimeSource.Monotonic.markNow()
        val original = buildList<Int> {
            repeat(100000) {
                this.add(compute(parse(readInput("Day24_mod")), number + "99999999"))
                number = number.decrement()
            }
        }

        println("Original file in ${start.elapsedNow()}")
        start = TimeSource.Monotonic.markNow()

        number = "999999"
        val reduced = buildList<Int> {
            repeat(100000) {
                this.add(computeDirect(number + "99999999"))
                number = number.decrement()
            }
        }
        println("Direct conversion in ${start.elapsedNow()}")

        assertEquals(original, reduced)

        assertEquals(0, computeDirect("99893999291967"))
        assertEquals(0, computeDirect("34171911181211"))
        assertEquals(compute(parse(readInput("Day24_orig")), "11111111111111"), computeDirect("11111111111111"))
        assertEquals(compute(parse(readInput("Day24_orig")), "22222222222222"), computeDirect("22222222222222"))
        assertEquals(compute(parse(readInput("Day24_orig")), "33333333333333"), computeDirect("33333333333333"))
        assertEquals(compute(parse(readInput("Day24_orig")), "44444444444444"), computeDirect("44444444444444"))
        assertEquals(compute(parse(readInput("Day24_orig")), "55555555555555"), computeDirect("55555555555555"))
        assertEquals(compute(parse(readInput("Day24_orig")), "66666666666666"), computeDirect("66666666666666"))
        assertEquals(compute(parse(readInput("Day24_orig")), "77777777777777"), computeDirect("77777777777777"))
        assertEquals(compute(parse(readInput("Day24_orig")), "88888888888888"), computeDirect("88888888888888"))
        assertEquals(compute(parse(readInput("Day24_orig")), "99999999999999"), computeDirect("99999999999999"))
    }

}
