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
    var lights: [1000][1000]bool = undefined;
    var lights2: [1000][1000]i32 = undefined;
    for (0..1000) |x| {
        for (0..1000) |y| {
            lights[x][y] = false;
            lights2[x][y] = 0;
        }
    }
    var it = std.mem.splitSequence(u8, text, "\n");
    while (it.next()) |line| {
        if (line.len == 0)
            continue;
        var tokens = std.mem.tokenizeSequence(u8, line, " ");
        const token = tokens.next() orelse "";
        if (std.mem.eql(u8, token, "toggle")) {
            // Toggle
            const coord1 = tokens.next() orelse "";
            const through = tokens.next() orelse "";
            if (!std.mem.eql(u8, through, "through")) {
                try stdout.print("Error *{s}*\n", .{through});
                try bw.flush(); // don't forget to flush!
                std.process.exit(1);
            }
            const coord2 = tokens.next() orelse "";
            var point1 = std.mem.tokenizeSequence(u8, coord1, ",");
            var point2 = std.mem.tokenizeSequence(u8, coord2, ",");

            const x1 = try std.fmt.parseInt(usize, point1.next() orelse "0", 10);
            const y1 = try std.fmt.parseInt(usize, point1.next() orelse "0", 10);

            const x2 = try std.fmt.parseInt(usize, point2.next() orelse "0", 10);
            const y2 = try std.fmt.parseInt(usize, point2.next() orelse "0", 10);

            for (x1..(x2 + 1)) |x| {
                for (y1..(y2 + 1)) |y| {
                    lights[x][y] = !lights[x][y];
                    lights2[x][y] += 2;
                }
            }
        } else if (std.mem.eql(u8, token, "turn")) {
            // Turn [on|off]
            var value = true;
            var value_part2: i32 = 1;
            if (std.mem.eql(u8, tokens.next() orelse "", "off")) {
                value = false;
                value_part2 = -1;
            }

            const coord1 = tokens.next() orelse "";
            const through = tokens.next() orelse "";
            if (!std.mem.eql(u8, through, "through")) {
                try stdout.print("Error *{s}*\n", .{through});
                try bw.flush(); // don't forget to flush!
                std.process.exit(1);
            }
            const coord2 = tokens.next() orelse "";
            var point1 = std.mem.tokenizeSequence(u8, coord1, ",");
            var point2 = std.mem.tokenizeSequence(u8, coord2, ",");

            const x1 = try std.fmt.parseInt(usize, point1.next() orelse "0", 10);
            const y1 = try std.fmt.parseInt(usize, point1.next() orelse "0", 10);

            const x2 = try std.fmt.parseInt(usize, point2.next() orelse "0", 10);
            const y2 = try std.fmt.parseInt(usize, point2.next() orelse "0", 10);

            for (x1..(x2 + 1)) |x| {
                for (y1..(y2 + 1)) |y| {
                    lights[x][y] = value;
                    lights2[x][y] += value_part2;
                    if (lights2[x][y] < 0) lights2[x][y] = 0;
                }
            }
        }
    }

    var part1: i64 = 0;
    var part2: i64 = 0;
    for (0..1000) |x| {
        for (0..1000) |y| {
            if (lights[x][y]) part1 += 1;
            part2 += lights2[x][y];
        }
    }

    try stdout.print("AoC2015 - Day06\n===============\n", .{});
    try stdout.print("total lights on - part 1 = {d}\n", .{part1});
    try stdout.print("total brightness - part 2 = {d}\n", .{part2});
    try bw.flush(); // don't forget to flush!
}
