#include <algorithm>
#include <cctype>
#include <cstddef>
#include <cstdlib>
#include <fstream>
#include <iostream>
#include <numeric>
#include <ranges>
#include <string_view>
#include <vector>

int64_t calibration_values(std::vector<std::string>& lines) {
  constexpr auto isntdigit = [](char c) { return !isdigit(c); };
  constexpr auto first_digit = std::views::drop_while(isntdigit);
  constexpr auto last_digit = std::views::reverse | first_digit;

  // Treat the first element of a range of character as a decimal digit
  constexpr auto decimal = [](auto&& rng) -> int64_t {
    return *rng.begin() - '0';
  };

  return std::accumulate(lines.begin(), lines.end(), static_cast<size_t>(0),
                         [&](int64_t acc, auto&& line) {
                           return acc + decimal(line | first_digit) * 10 +
                                  decimal(line | last_digit);
                         });
}

constexpr std::array<std::string_view, 19> digits = {
    "0",   "1",   "2",     "3",    "4",    "5",   "6",     "7",     "8",   "9",
    "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

int64_t to_digit(std::string_view str) {
  // Find the digit which is prefix of our current string (if any)
  auto match = std::ranges::find_if(
      digits, [&](auto&& digit) { return str.starts_with(digit); });
  if (match == digits.end()) return -1;

  // Turn the position in the array of digits into a digit
  int64_t digit = std::distance(digits.begin(), match);
  if (digit >= 10) return digit - 9;
  return digit;
}

int64_t spelled_out(std::vector<std::string>& lines) {
  return std::accumulate(
      lines.begin(), lines.end(), static_cast<size_t>(0),
      [&](int64_t acc, auto&& line) {
        int64_t first = -1;
        int64_t last = -1;
        // We want to iterate over all the suffixes
        for (auto pos : std::views::iota(line.begin(), line.end())) {
          // Current suffix
          auto substr = std::string_view(pos, line.end());

          // Check if it begins with a digit and update first and last
          if (int64_t digit = to_digit(substr); digit != -1) {
            if (first == -1) first = digit;
            last = digit;
          }
        }
        return acc + first * 10 + last;
      });
}

int main(int argc, char* argv[]) {
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

  std::vector<std::string> lines;
  std::string line;
  while (std::getline(in, line)) {
    lines.push_back(line);
  }

  std::cout << "AoC2023 - Day01\n===============\n";
  std::cout << "The sum of calibration values is: " << calibration_values(lines)
            << "\n";
  std::cout << "The sum of calibration values with spelled out digits is: "
            << spelled_out(lines) << "\n";
  return EXIT_SUCCESS;
}
