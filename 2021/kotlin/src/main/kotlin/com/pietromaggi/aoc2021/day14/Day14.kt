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

package com.pietromaggi.aoc2021.day14

import com.pietromaggi.aoc2021.readInput

data class Rule(val a: Char, val b: Char)

fun parseInput(input: List<String>): Pair<String, Map<Rule, Char>> {
    val template = input[0].trim()
    val rules = mutableMapOf<Rule, Char>()
    input.takeLastWhile { it.isNotBlank() }
        .map { line ->
            val (a, b) = line.split(" -> ")
            rules[Rule(a[0], a[1])] = b[0]
        }

    return Pair(template, rules)
}

fun compute(input: List<String>, steps: Int): Long {
    val (template, rules) = parseInput(input)
    var polymer = mutableMapOf<Rule, Long>()
    template.zipWithNext().forEach { (first, second) ->
        val rule = Rule(first, second)
        polymer[rule] = polymer.getOrDefault(rule, 0) + 1
    }
    for (step in 1..steps) {
        val temp = mutableMapOf<Rule, Long>()
        polymer.forEach { (rule, count) ->
            val newElement = rules[rule]!!
            val rule1 = Rule(rule.a, newElement)
            val rule2 = Rule(newElement, rule.b)
            temp[rule1] = temp.getOrDefault(rule1, 0) + count
            temp[rule2] = temp.getOrDefault(rule2, 0) + count
        }
        polymer = temp
    }
    val polymerFrequency = mutableMapOf<Char, Long>()
    polymer.forEach { (rule, value) ->
        polymerFrequency[rule.a] = polymerFrequency.getOrDefault(rule.a, 0) + value
        polymerFrequency[rule.b] = polymerFrequency.getOrDefault(rule.b, 0) + value
    }
    polymerFrequency[template.first()] = polymerFrequency[template.first()]!! + 1
    polymerFrequency[template.last()] = polymerFrequency[template.last()]!! + 1
    val finalCount = polymerFrequency.map { (_, value) -> value / 2 }.sorted()
    return finalCount.last() - finalCount.first()
}

fun main() {
    val input = readInput("Day14")
    println(compute(input, 10))
    println(compute(input, 40))
}
