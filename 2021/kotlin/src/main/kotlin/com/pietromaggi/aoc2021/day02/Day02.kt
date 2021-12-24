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

enum class Command {
    FORWARD,
    DOWN,
    UP
}

data class Instruction(val command: Command, val value: Int)

fun String.toCommand() : Command {
    val command = when(this) {
        "forward" -> Command.FORWARD
        "down" -> Command.DOWN
        "up" -> Command.UP
        else -> throw IllegalArgumentException("Invalid command")
    }
    return command
}

fun List<String>.toInstructions() : List<Instruction> {
    return this.map { line ->
        val (command, value) = line.split(" ")
        Instruction(command.toCommand(), value.toInt())
    }
}

fun part1(input: List<String>) : Int {
    val instructions = input.toInstructions()
    var position = 0
    var depth = 0
    for ((command, value) in instructions) {
        when (command) {
            Command.FORWARD -> position += value
            Command.DOWN -> depth += value
            Command.UP -> depth -= value
        }
    }
    return depth * position
}

fun part2(input: List<String>) : Int {
    val instructions = input.toInstructions()
    var position = 0
    var aim = 0
    var depth = 0
    for ((command, value) in instructions) {
        when (command) {
            Command.FORWARD -> {
                position += value
                depth += value * aim
            }
            Command.DOWN -> aim += value
            Command.UP -> aim -= value
        }
    }
    return depth * position
}

fun main() {
    val input = readInput("Day02")
    println("What do you get if you multiply your final horizontal position by your final depth?")
    println("My puzzle answer is: ${part1(input)}")
    println("What do you get if you multiply your final horizontal position by your final depth?")
    println("My puzzle answer is: ${part2(input)}")}
