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

// Source: https://github.com/elizarov/AdventOfCode2021/blob/main/src/Day18.kt

sealed class SnailfishNumber {
    class Single(val value: Int) : SnailfishNumber() {
        override fun toString(): String = value.toString()
    }
    class Pair(val left: SnailfishNumber, val right: SnailfishNumber) : SnailfishNumber() {
        override fun toString(): String = "[$left,$right]"
    }
}

fun parseInput(input: String): SnailfishNumber {
    var pos = 0
    fun parse(): SnailfishNumber {
        if (input[pos] == '[') {
            pos++
            val left = parse()
            pos++ // skip ','
            val right = parse()
            pos++ // skip ']'
            return SnailfishNumber.Pair(left, right)
        }
        val start = pos
        while (input[pos] in '0'..'9') pos++
        return SnailfishNumber.Single(input.substring(start, pos).toInt())
    }
    return parse()
}

fun SnailfishNumber.findPair(n: Int): SnailfishNumber.Pair? {
    if (n == 0) return this as? SnailfishNumber.Pair?
    if (this is SnailfishNumber.Pair) {
        left.findPair(n - 1)?.let { return it }
        right.findPair(n - 1)?.let { return it }
    }
    return null
}

fun SnailfishNumber.findReg(lim: Int): SnailfishNumber.Single? = when(this) {
    is SnailfishNumber.Single -> if (value >= lim) this else null
    is SnailfishNumber.Pair -> {
        left.findReg(lim)?.let { return it }
        right.findReg(lim)?.let { return it }
        null
    }
}

/*
 * Recursively construct a list of Snailfish numbers
 */
fun SnailfishNumber.traverse(keep: SnailfishNumber.Pair): List<SnailfishNumber> = when(this) {
    is SnailfishNumber.Single -> listOf(this)
    is SnailfishNumber.Pair -> if (this == keep) listOf(this) else left.traverse(keep) + right.traverse(keep)
}

fun SnailfishNumber.replace(op: Map<SnailfishNumber, SnailfishNumber>): SnailfishNumber {
    op[this]?.let { return it }
    return when(this) {
        is SnailfishNumber.Single -> this
        is SnailfishNumber.Pair -> SnailfishNumber.Pair(left.replace(op), right.replace(op))
    }
}

fun SnailfishNumber.reduceOp(): Map<SnailfishNumber, SnailfishNumber>? {
    // Check for pairs to explode
    val pairToExplode = findPair(4)
    if ((pairToExplode != null) &&
        (pairToExplode.left is SnailfishNumber.Single) &&
        (pairToExplode.right is SnailfishNumber.Single)) {
        val updates = mutableMapOf<SnailfishNumber, SnailfishNumber>(pairToExplode to SnailfishNumber.Single(0))
        val localListOfSnailfishNumber = traverse(pairToExplode)
        val indexOfPairToExplode = localListOfSnailfishNumber.indexOf(pairToExplode)
        (localListOfSnailfishNumber.getOrNull(indexOfPairToExplode - 1) as? SnailfishNumber.Single)?.let { left ->
            updates[left] = SnailfishNumber.Single(left.value + pairToExplode.left.value)
        }
        (localListOfSnailfishNumber.getOrNull(indexOfPairToExplode + 1) as? SnailfishNumber.Single)?.let { right ->
            updates[right] = SnailfishNumber.Single(right.value + pairToExplode.right.value)
        }
        return updates
    }
    // Check for numbers to split
    val valueToSplit = findReg(10)
    return if (valueToSplit != null) {
        mapOf(valueToSplit to SnailfishNumber.Pair(
            SnailfishNumber.Single(valueToSplit.value / 2),
            SnailfishNumber.Single((valueToSplit.value + 1) / 2)))
    } else {
        null
    }
}

fun add(a: SnailfishNumber, b: SnailfishNumber): SnailfishNumber =
    generateSequence<SnailfishNumber>(SnailfishNumber.Pair(a, b)) { snailfishNumber ->
        snailfishNumber.reduceOp()?.let { newValue ->
            snailfishNumber.replace(newValue)
        }
    }.last()

fun SnailfishNumber.magnitude(): Int = when(this) {
    is SnailfishNumber.Single -> value
    is SnailfishNumber.Pair -> 3 * left.magnitude() + 2 * right.magnitude()
}

fun part1(numbers: List<SnailfishNumber>) = numbers.reduce(::add).magnitude()

fun part2(numbers: List<SnailfishNumber>): Int {
    val result = buildList {
        for (i in numbers.indices)
            for (j in numbers.indices)
                if (i != j) add(add(numbers[i], numbers[j]).magnitude())
    }
    return result.maxOrNull()!!
}

fun main() {
    val input = readInput("Day18")
    val numbers = input.map(::parseInput)
    println("What is the magnitude of the final sum?")
    println("My puzzle answer is: ${part1(numbers)}")
    println("What is the largest magnitude of any sum of two different snailfish numbers from the homework assignment?")
    println("My puzzle answer is: ${part2(numbers)}")
}
