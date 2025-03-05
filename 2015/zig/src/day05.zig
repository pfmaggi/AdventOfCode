// Copyright 2025 Pietro F. Maggi
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

const std = @import("std");
const process = std.process;
const fs = std.fs;

fn isNiceStringPart1(str: []const u8) bool {
    var vowel_count: u32 = 0;
    var has_double: bool = false;

    for (str, 0..) |c, i| {
        switch (c) {
            'a', 'e', 'i', 'o', 'u' => vowel_count += 1,
            else => {},
        }

        if (i > 0 and str[i - 1] == c) {
            has_double = true;
        }
    }

    if (vowel_count < 3 or !has_double) {
        return false;
    }

    if (std.mem.indexOf(u8, str, "ab") != null or
        std.mem.indexOf(u8, str, "cd") != null or
        std.mem.indexOf(u8, str, "pq") != null or
        std.mem.indexOf(u8, str, "xy") != null)
    {
        return false;
    }

    return true;
}

fn isNiceStringPart2(str: []const u8) bool {
    var has_pair: bool = false;
    var has_repeat: bool = false;

    for (str, 0..) |c, i| {
        if (i < str.len - 1) {
            const pair = str[i .. i + 2];
            if (std.mem.indexOf(u8, str[i + 2 ..], pair) != null) {
                has_pair = true;
            }
        }

        if (i < str.len - 2 and c == str[i + 2]) {
            has_repeat = true;
        }
    }

    return has_pair and has_repeat;
}

pub fn main() anyerror!void {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();
    const stdout_file = std.io.getStdOut().writer();
    var bw = std.io.bufferedWriter(stdout_file);
    const stdout = bw.writer();

    var arg_it = try process.argsWithAllocator(allocator);

    // First arg is the executable name
    const arg_exe = arg_it.next() orelse "";

    const filename = arg_it.next() orelse {
        try stdout.print("Please enter filename to input data:\n", .{});
        try stdout.print("> {s} <filename>\n", .{arg_exe});
        return error.InvalidArgs;
    };

    const limit = 1 * 1024 * 1024 * 1024;
    const text = try fs.cwd().readFileAlloc(allocator, filename, limit);
    defer allocator.free(text);

    var nicePart1: u32 = 0;
    var nicePart2: u32 = 0;
    var it = std.mem.splitSequence(u8, text, "\n");
    while (it.next()) |line| {
        if (line.len == 0)
            continue;
        if (isNiceStringPart1(line)) {
            nicePart1 += 1;
        }
        if (isNiceStringPart2(line)) {
            nicePart2 += 1;
        }
    }

    try stdout.print("AoC2015 - Day05\n===============\n", .{});
    try stdout.print("How Many nice strings - Part1: {d}\n", .{nicePart1});
    try stdout.print("How Many nice strings - Part2: {d}\n", .{nicePart2});
    try bw.flush(); // don't forget to flush!
}

test "isNiceString" {
    try std.testing.expect(isNiceStringPart1("ugknbfddgicrmopn"));
    try std.testing.expect(isNiceStringPart1("aaa"));
    try std.testing.expect(!isNiceStringPart1("jchzalrnumimnmhp"));
    try std.testing.expect(!isNiceStringPart1("haegwjzuvuyypxyu"));
    try std.testing.expect(!isNiceStringPart1("dvszwmarrgswjxmb"));
}

test "isNiceStringPart2" {
    try std.testing.expect(isNiceStringPart2("qjhvhtzxzqqjkmpb"));
    try std.testing.expect(isNiceStringPart2("xxyxx"));
    try std.testing.expect(!isNiceStringPart2("uurcxstgmygtbstg"));
    try std.testing.expect(!isNiceStringPart2("ieodomkazucvgmuy"));
}
