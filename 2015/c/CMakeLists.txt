cmake_minimum_required(VERSION 3.10)

# set the project name
project(AoC_2015)

# Set the output folder where your program will be created
set(CMAKE_RUNTIME_OUTPUT_DIRECTORY ${CMAKE_BINARY_DIR}/build)

# Compile
add_executable(day01 "${PROJECT_SOURCE_DIR}/src/day01.c")
add_executable(day02 "${PROJECT_SOURCE_DIR}/src/day02.c")
add_executable(day03 "${PROJECT_SOURCE_DIR}/src/day03.c")
