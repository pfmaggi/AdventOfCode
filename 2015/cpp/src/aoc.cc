/*
 * Copyright 2025 Pietro F. Maggi
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

#include "aoc.h"
#include <fmt/core.h>
#include "absl/strings/str_cat.h"


int main(int argc, char *argv[]) {
  if (argc < 2) {
    fmt::print("Please enter path to input data files:\n> {} <path>\n", argv[0]);

    std::exit(EXIT_FAILURE);
  }

  auto [count, position] = day_01(absl::StrCat(argv[1], "/day01.txt"));

  fmt::print("AoC2015 - Day01\n===============\n");
  fmt::print("Part 1 - Final floor is: {}\n", count);
  fmt::print("Part 2 - Entered the basement at position: {}\n", position);

  return EXIT_SUCCESS;
}

