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

struct Line {
  std::string data;
};

std::istream& operator>>(std::istream& is, Line& line) {
  std::getline(is, line.data);
  return is;
}

std::tuple<int, int, int> parse_dimensions(const std::string& input) {
  std::stringstream ss(input);
  int w, h, l;
  char x1, x2;

  if (ss >> w >> x1 >> h >> x2 >> l && x1 == 'x' && x2 == 'x') {
    return std::tuple<int, int, int>{w, h, l};
  } else {
    // Handle parsing error
    std::cerr << "Invalid input format: " << input << '\n';
    return std::tuple<int, int, int>{0, 0, 0};
  }
}

std::pair<long, long> process_dimensions(int w, int h, int l) {
  int b1{w * h};
  int b2{w * l};
  int b3{h * l};
  int min{std::min({b1, b2, b3})};
  int max{std::max({w, h, l})};

  long total_paper{min + 2 * (b1 + b2 + b3)};
  long total_ribbon{2 * (w + h + l - max) + (w * h * l)};

  return std::pair<long, long>{total_paper, total_ribbon};
}

std::pair<long, long> day_02(const std::string& filename) {
  std::ifstream in(filename);
  if (!in) {
    std::cerr << "File opening failed: " << filename << '\n';

    std::exit(EXIT_FAILURE);
  }

  auto result = std::accumulate(
      std::istream_iterator<Line>(in), std::istream_iterator<Line>(),
      std::pair<long, long>{0L, 0L},
      [](std::pair<long, long> acc, const Line& line) {
        auto [total_paper, total_ribbon] = acc;
        auto [w, h, l] = parse_dimensions(line.data);
        auto [paper, ribbon] = process_dimensions(w, h, l);
        return std::pair<long, long>{total_paper + paper,
                                     total_ribbon + ribbon};
      });

  in.close();
  return result;
}
