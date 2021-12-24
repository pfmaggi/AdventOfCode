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

package com.pietromaggi.aoc2021.day08

import com.pietromaggi.aoc2021.readInput
import java.util.Collections.swap

// from https://gist.github.com/dmdrummond/4b1d8a4f024183375f334a5f0a984718
fun <V> List<V>.permutations(): List<List<V>> {
    val retVal: MutableList<List<V>> = mutableListOf()

    fun generate(k: Int, list: List<V>) {
        // If only 1 element, just output the array
        if (k == 1) {
            retVal.add(list.toList())
        } else {
            for (i in 0 until k) {
                generate(k - 1, list)
                if (k % 2 == 0) {
                    swap(list, i, k - 1)
                } else {
                    swap(list, 0, k - 1)
                }
            }
        }
    }

    generate(this.count(), this.toList())
    return retVal
}

fun part1(input: List<String>) =
    input.map {
        val (_, digits) = it.split(" | ")
        digits.split(" ").map(String::length)
    }.sumOf { line ->
        line.count { len ->
            (len == 2) or (len == 3) or (len == 4) or (len == 7)
        }
    }

fun part2(input: List<String>): Int {
    val segmentsMapping = mapOf(
        setOf('A', 'B', 'C', 'E', 'F', 'G') to 0,
        setOf('C', 'F') to 1,
        setOf('A', 'C', 'D', 'E', 'G') to 2,
        setOf('A', 'C', 'D', 'F', 'G') to 3,
        setOf('B', 'C', 'D', 'F') to 4,
        setOf('A', 'B', 'D', 'F', 'G') to 5,
        setOf('A', 'B', 'D', 'E', 'F', 'G') to 6,
        setOf('A', 'C', 'F') to 7,
        setOf('A', 'B', 'C', 'D', 'E', 'F', 'G') to 8,
        setOf('A', 'B', 'C', 'D', 'F', 'G') to 9
    )

    // evaluate all the 5040 permutations :-(
    val inputCables = ('A'..'G').toList()
    val inputChars = ('a'..'g').toList()
    val permutations = inputChars.permutations().map { perm -> perm.zip(inputCables).toMap() }

    fun bruteForceIt(words: List<String>, expectedNumbers: List<String>): Int {
        fun getMapping(): Map<Char, Char> {
            evaluateNext@ for (perm in permutations) {
                for (word in words) {
                    val mapped = word.map { perm[it]!! }.toSet()
                    if (!segmentsMapping.containsKey(mapped)) continue@evaluateNext
                }
                return perm
            }
            // It should never happen...
            return mapOf()
        }

        val mapping = getMapping()
        val num = expectedNumbers.joinToString("") { digit ->
            val segments = digit.map { mapping[it]!! }.toSet()
            val dig = segmentsMapping[segments]!!
            "$dig"
        }
        return num.toInt()
    }

    val lists = input.map {
        val (signals, digits) = it.split(" | ")
        signals.split(" ") to digits.split(" ")
    }
    val result = lists.sumOf { (input, output) ->
        bruteForceIt(input, output)
    }
    return result
}

fun main() {
    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
