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

package com.pietromaggi.aoc2021.day05

import com.pietromaggi.aoc2021.readInput


class AoCPoint(val x: Int, val y: Int) : Comparable<AoCPoint> {
    constructor(point: List<Int>) : this(point[0], point[1])

    override fun compareTo(other: AoCPoint): Int {
        if (this.x != other.x) {
            return this.x - other.x
        }
        return this.y - other.y
    }

    operator fun rangeTo(that: AoCPoint) = AoCPointRange(this, that)

    operator fun inc(): AoCPoint {
        return AoCPoint(this.x + 1, this.y + 1)
    }

    override fun toString(): String = "$x, $y"
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AoCPoint

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}

class AoCPointRange(
    override val start: AoCPoint,
    override val endInclusive: AoCPoint,
) : ClosedRange<AoCPoint>, Iterable<AoCPoint> {

    override fun iterator(): Iterator<AoCPoint> {
        return AoCPointIterator(start, endInclusive)
    }
}

class AoCPointIterator(private val start: AoCPoint, private val endInclusive: AoCPoint) : Iterator<AoCPoint> {
    private var initValue = start
    private var currentValue = start
    private var incrementX = if (start.x < endInclusive.x) 1 else -1
    private var incrementY = if (start.y < endInclusive.y) 1 else -1

    override fun hasNext(): Boolean {
        return currentValue != endInclusive
    }

    override fun next(): AoCPoint {
        currentValue = initValue
        initValue = if (start.x == endInclusive.x) {
            AoCPoint(initValue.x, initValue.y + incrementY)
        } else if (start.y == endInclusive.y) {
            AoCPoint(initValue.x + incrementX, initValue.y)
        } else {
            AoCPoint(initValue.x + incrementX, initValue.y + incrementY)
        }
        return currentValue
    }
}

fun compute(input: List<String>, full: Boolean): Int {
    val fields: MutableMap<AoCPoint, Int> = mutableMapOf()
    input.map { it.split(" -> ") }.map { line ->
        val a = line[0].split(",").map { it.toInt() }
        val b = line[1].split(",").map { it.toInt() }
        val pointA = AoCPoint(a)
        val pointB = AoCPoint(b)
        if (full or (pointA.x == pointB.x) or (pointA.y == pointB.y)) {
            // Only process horizontal and vertical lines if full == false
            for (point in pointA..pointB) {
                fields[point] = fields.getOrDefault(point, 0) + 1
            }
        }
    }
    return fields.filterValues { it > 1 }.count()
}

fun main() {
    val input = readInput("Day05")
    println(compute(input, false))
    println(compute(input, true))
}
