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

package com.pietromaggi.aoc2021.day12

import com.pietromaggi.aoc2021.readInput

fun buildPaths(connections: Map<String, MutableList<String>>, path: List<String>, validPaths: MutableSet<List<String>>, canVisitTwice: String) {
    val currentCave = path.last()
    if (currentCave == "end") {
        validPaths += path
        return
    }
    val nextCavesToVisit = connections.getValue(currentCave).filter { cave ->
        (cave.isLargeCave) or
                (cave !in (path)) or
                ((cave == canVisitTwice) and (path.count { it == canVisitTwice } <= 1))
    }
    for (nextCave in nextCavesToVisit) {
        buildPaths(connections, path + nextCave, validPaths, canVisitTwice)
    }
}

fun part1(connections: Map<String, MutableList<String>>): Int {
    val results = mutableSetOf<List<String>>()

    buildPaths(connections, listOf("start"), results, "")
    return results.count()
}

fun part2(connections: Map<String, MutableList<String>>): Int {
    val results = mutableSetOf<List<String>>()

    for (smallCave in connections.keys.filter { cave -> (!cave.isLargeCave) && cave !in listOf("start", "end") }) {
        buildPaths(connections, listOf("start"), results, smallCave)
    }
    return results.count()
}

fun buildConnections(fileName: String) : Map<String, MutableList<String>>  {
    return buildMap {
        readInput(fileName).forEach { line ->
            val (cave1, cave2) = line.split("-")
            this.getOrPut(cave1) { mutableListOf() }.add(cave2)
            this.getOrPut(cave2) { mutableListOf() }.add(cave1)
        }
    }
}

val String.isLargeCave get() = this != lowercase()

fun main() {
    val connections = buildConnections("Day12")
    println(part1(connections))
    println(part2(connections))
}
