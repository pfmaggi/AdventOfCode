// Copyright 2022 Pietro F. Maggi
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

pub fn main() anyerror!void {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();
    const stdout = std.io.getStdOut().writer();

    var arg_it = process.args();

    // First arg is the executable name
    var arg_exe = arg_it.next(allocator);

    const filename = try (arg_it.next(allocator) orelse {
        try stdout.print("Please enter filename to input data:\n", .{});
        try stdout.print("> {s} <filename>\n", .{arg_exe});
        return error.InvalidArgs;
    });

    const limit = 1 * 1024 * 1024 * 1024;
    const text = try fs.cwd().readFileAlloc(allocator, filename, limit);
    defer allocator.free(text);

    var sum: u64 = 0;
    var total_fuel: u64 = 0;
    var it = std.mem.split(u8, text, "\n");
    while (it.next()) |line| {
        if (line.len == 0)
            continue;
        const trimmed = std.mem.trim(u8, line, " \n\r\t");
        var fuel = try std.fmt.parseInt(u32, trimmed, 10);
        fuel = (fuel / 3) - 2;
        sum += fuel;
        while (fuel > 6) {
            fuel = (fuel/3) - 2;
            total_fuel += fuel;
        }
    }

    total_fuel += sum;

    try stdout.print("AoC2019 - Day01\n===============\n", .{});
    try stdout.print("Part 1 total fuel: {d}\n", .{sum});
    try stdout.print("Part 2 total fuel: {d}\n", .{total_fuel});
}
