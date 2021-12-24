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

import com.pietromaggi.aoc2021.readInput

data class PacketInfo(val version: Int, val value: Long, val position: Int)

fun compute(operation: Int, factors: List<Long>): Long = when (operation) {
    0 -> {
        // Sum
        factors.fold(0L) { sum, element -> sum + element }
    }
    1 -> {
        // Product
        factors.fold(1L) { product, element -> product * element }
    }
    2 -> {
        // Minimum
        factors.minOrNull()!!
    }
    3 -> {
        // Maximum
        factors.maxOrNull()!!
    }
    5 -> {
        // Greater Than
        if (factors.first() > factors.last()) 1L else 0L
    }
    6 -> {
        // Lesser Than
        if (factors.first() < factors.last()) 1L else 0L
    }
    7 -> {
        // Equal to
        if (factors.first() == factors.last()) 1L else 0L
    }
    else -> 0L
}

fun parseCalc(code: String): PacketInfo {
    var pos = 0
    var version = code.substring(pos..pos + 2).toInt(radix = 2)
    pos += 3
    val type = code.substring(pos..pos + 2).toInt(radix = 2)
    pos += 3
    val result = when (type) {
        4 -> {
            // Literal value
            var number = code.substring(pos+1..pos+4)
            while (code[pos] != '0') {
                pos += 5
                number += code.substring(pos+1..pos + 4)
            }
            pos += 5
            number.toLong(radix = 2)
        }
        else -> {
            val lengthType = code.substring(pos..pos).toInt(radix = 2)
            val length: Int
            val numbers = mutableListOf<Long>()
            pos += 1
            if (lengthType == 0) {
                // length is 15 bits
                length = code.substring(pos..pos + 14).toInt(radix = 2)
                pos += 15
                val start = pos
                while (pos < start + length) {
                    val (partialVersion, partialValue, digested) = parseCalc(code.substring(pos))
                    numbers.add(partialValue)
                    version += partialVersion
                    pos += digested
                }
                compute(type, numbers)
            } else {
                // number of sub-packets is 11 bits
                val subPackets = code.substring(pos..pos + 10).toInt(radix = 2)
                pos += 11
                repeat(subPackets) {
                    val (partialVersion, partialValue, digested) = parseCalc(code.substring(pos))
                    numbers.add(partialValue)
                    version += partialVersion
                    pos += digested
                }
                compute(type, numbers)
            }
        }
    }
    return PacketInfo(version, result, pos)
}

fun part1(input: List<String>): Int {
    val line =
        input[0].chunked(1).joinToString("") { (it.toInt(radix = 16)).toString(radix = 2).padStart(4, '0') }

    val (result, _, _) = parseCalc(line)

    return result
}

fun part2(input: List<String>): Long {
    val line =
        input[0].chunked(1).joinToString("") { (it.toInt(radix = 16)).toString(radix = 2).padStart(4, '0') }

    val (_, result, _) = parseCalc(line)

    return result
}

fun main() {
    val input = readInput("Day16")
    println(part1(input))
    println(part2(input))
}
