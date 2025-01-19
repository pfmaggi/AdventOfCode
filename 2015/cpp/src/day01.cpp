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

#include <cstdlib>
#include <fstream>
#include <iostream>
#include <numeric>

int main(int argc, char *argv[]) {
  if (argc < 2) {
    std::cout << "Please enter filename to input data:\n";
    std::cout << "> " << argv[0] << " <filename>\n";

    std::exit(EXIT_FAILURE);
  }

  // Open the file
  std::ifstream in(argv[1]);

  if (!in) {
    std::cout << "File opening failed\n";

    std::exit(EXIT_FAILURE);
  }

  auto position{0};
  auto count = std::accumulate(
      std::istreambuf_iterator<char>(in), std::istreambuf_iterator<char>(), 0L,
      [i = 0, &position](auto counter, auto val) mutable {
        i++;
        if ('(' == val) counter++;
        if (')' == val) counter--;
        if ((0 == position) && (counter < 0)) position = i;
        return counter;
      });

  std::cout << "AoC2015 - Day01\n===============\n";
  std::cout << "Final floor is: " << count << '\n';
  std::cout << "Entered the basement at position: " << position << '\n';

  return EXIT_SUCCESS;
}
