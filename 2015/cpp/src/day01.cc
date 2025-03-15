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

std::pair<int, long> day_01(const std::string& filename) {
  // Open the file
  std::ifstream in(filename);

  if (!in) {
    std::cerr << "File opening failed: " << filename << '\n';

    std::exit(EXIT_FAILURE);
  }

  auto position{0};
  auto count = std::accumulate(
      std::istreambuf_iterator<char>(in), std::istreambuf_iterator<char>(), 0L,
      [i{0L}, &position](auto counter, auto val) mutable {
        i++;
        if ('(' == val) counter++;
        if (')' == val) counter--;
        if ((0 == position) && (counter < 0)) position = i;
        return counter;
      });

  in.close();
  return std::pair<int, long>{count, position};
}
