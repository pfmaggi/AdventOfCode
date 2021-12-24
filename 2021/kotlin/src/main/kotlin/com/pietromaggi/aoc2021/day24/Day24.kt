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

enum class Op {
    INP,
    ADD,
    MUL,
    DIV,
    MOD,
    EQL
}

enum class Reg {
    W,
    X,
    Y,
    Z
}

data class Instruction(val op: Op, val reg1: Reg?, val reg2: Reg?)

fun String.toReg() : Reg? =
    when (this[0]) {
        'w' -> W
        'x' -> X
        'y' -> Y
        'z' -> Z
        else -> null
    }

fun part1(input: List<String>): Int {
    val program = listOf<Instruction>()
    input.map { line ->
                    when (line.substring(0..2)) {
                        "inp" -> {
                            program.add(Instruction(INP, line.substring(4).toReg(), null))
                        }
                        "add" -> {
                            program.add(Instruction(ADD, line.substring(4..6).toReg(), line.substring(6).toReg()))
                        }
                        "mul" -> {
                            program.add(Instruction(MUL, line.substring(4..6).toReg(), line.substring(6).toReg()))
                        }
                        "div" -> {
                            program.add(Instruction(DIV, line.substring(4..6).toReg(), line.substring(6).toReg()))
                        }
                        "mod" -> {
                            program.add(Instruction(MOD, line.substring(4..6).toReg(), line.substring(6).toReg()))
                        }
                        "eql" -> {
                            program.add(Instruction(EQL, line.substring(4..6).toReg(), line.substring(6).toReg()))
                        }
                    }
    }

    fun inp(reg: Reg, digit: Char) {
       Regs[Reg] = digit.digitToInt()
    }

    fun add(reg1: Reg?, reg2: Reg?) {
        if ((reg1 == null) or (reg2 == null)) return
        Regs[reg1] = Regs[reg1] + Regs[reg2]
    }

    fun mul(reg1: Reg?, reg2: Reg?) {
        if ((reg1 == null) or (reg2 == null)) return
        Regs[reg1] = Regs[reg1] * Regs[reg2]
    }

    fun div(reg1: Reg?, reg2: Reg?) {
        if ((reg1 == null) or (reg2 == null)) return
        Regs[reg1] = Regs[reg1] / Regs[reg2]
    }

    fun mod(reg1: Reg?, reg2: Reg?) {
        if ((reg1 == null) or (reg2 == null)) return
        Regs[reg1] = Regs[reg1] % Regs[reg2]
    }

    fun eql(reg1: Reg?, reg2: Reg?) {
        if ((reg1 == null) or (reg2 == null)) return
        Regs[reg1] = if (Regs[reg1] == Regs[reg2]) 1 else 0
    }

    var Regs = buildMapOf<Reg, Int>() {
        add(W, 0)
        add(X, 0)
        add(Y, 0)
        add(Z, 0)
        }
    var number = "12572468999999"
    var digit = 0
    for (instruction in program) {
        when (instruction.op) {
            INP -> inp(reg1, number[digit++])
            ADD -> add(reg1, reg2)
            MUL -> mul(reg1, reg2)
            DIV -> div(reg1, reg2)
            MOD -> mod(reg1. reg2)
            EQL -> eql(reg1, reg2)
        }
    }

    return Regs[Z]!!
}

fun part2(input: List<String>): Long {

    return 1
}

fun main() {
    val input = readInput("Day24")
    println("""
    ### DAY 24 ###
    ==============
    What is the largest model number accepted by MONAD?
    --> ${part1(input)}
    Part 2?
    --> ${part2(input)}
    """)
}
