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

package com.pietromaggi.aoc2021.day22

import com.pietromaggi.aoc2021.readInput

fun part1(input: List<String>): Int {
    data class Point(val x: Int, val y: Int, val z: Int)

    val cuboids = mutableSetOf<Point>()
    input.forEach { line ->
        val items = line.split(",")
        val on = items[0].startsWith("on")
        val (x1, x2) = items[0].removePrefix("on x=").removePrefix("off x=").split("..").map(String::toInt)
        val (y1, y2) = items[1].removePrefix("y=").split("..").map(String::toInt)
        val (z1, z2) = items[2].removePrefix("z=").split("..").map(String::toInt)

        if ((x1 in -50..50) and (y1 in -50..50) and (z1 in -50..50)) {
            x1.coerceAtLeast(-50)
            x2.coerceAtMost(50)
            y1.coerceAtLeast(-50)
            y2.coerceAtMost(50)
            z1.coerceAtLeast(-50)
            z2.coerceAtMost(50)
            for (x in x1..x2) for (y in y1..y2) for (z in z1..z2)
                if (on)
                    cuboids.add(Point(x, y, z))
                else
                    cuboids.remove(Point(x, y, z))
        }
    }

    return cuboids.count()
}

fun part2(input: List<String>): Long {
    data class Cube(
        val x1: Int, val x2: Int,
        val y1: Int, val y2: Int,
        val z1: Int, val z2: Int,
        val sign: Int,
    )

    var cuboids = listOf<Cube>()
    input.forEach { line ->
        val items = line.split(",")
        val sign = if (items[0].startsWith("on")) 1 else -1
        val (nx1, nx2) = items[0].removePrefix("on x=").removePrefix("off x=").split("..").map(String::toInt)
        val (ny1, ny2) = items[1].removePrefix("y=").split("..").map(String::toInt)
        val (nz1, nz2) = items[2].removePrefix("z=").split("..").map(String::toInt)

        val temp = mutableListOf<Cube>()
        for ((ex1, ex2, ey1, ey2, ez1, ez2, esign) in cuboids) {
            temp.add(Cube(ex1, ex2, ey1, ey2, ez1, ez2, esign))
            val ix1 = nx1.coerceAtLeast(ex1)
            val ix2 = nx2.coerceAtMost(ex2)
            val iy1 = ny1.coerceAtLeast(ey1)
            val iy2 = ny2.coerceAtMost(ey2)
            val iz1 = nz1.coerceAtLeast(ez1)
            val iz2 = nz2.coerceAtMost(ez2)
            if ((ix1 <= ix2) and (iy1 <= iy2) and (iz1 <= iz2)) {
                temp.add(Cube(ix1, ix2, iy1, iy2, iz1, iz2, -esign))
            }
        }
        if (sign > 0) {
            temp.add(Cube(nx1, nx2, ny1, ny2, nz1, nz2, sign))
        }

        cuboids = temp
    }

    return cuboids.map { cube ->
        (cube.x2 - cube.x1 + 1).toLong() * (cube.y2 - cube.y1 + 1) * (cube.z2 - cube.z1 + 1) * cube.sign
    }.fold(0L) { sum, element ->
        sum + element
    }
}

fun main() {
    val input = readInput("Day22")
    println("""    
    ### DAY 22 ###
    ==============
    how many cubes are on?
    --> ${part1(input)}
    how many cubes are on?
    --> ${part2(input)}
    """)
}