// Copyright 2021 Pietro F. Maggi
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

// We assume that a map 200x200 is big enough
// and we start from the middle position 100,100.
fn part_1(text: []u8) u32 {
    var pos_x: u32 = 100;
    var pos_y: u32 = 100;
    var count: u32 = 0;
    var map: [200][200]u8 = undefined;
    for (text) |c| {
        switch (c) {
            '>' => {
                pos_x += 1;
            },
            '<' => {
                pos_x -= 1;
            },
            '^' => {
                pos_y += 1;
            },
            'v' => {
                pos_y -= 1;
            },
            else => {},
        }
        if (map[pos_x][pos_y] != 'x') {
            map[pos_x][pos_y] = 'x';
            count += 1;
        }
    }

    return count;
}

// We assume that a map 200x200 is big enough
// and we start from the middle position 100,100.
fn part_2(text: []u8) u32 {
    var pos_x = [_]u32{100, 100};
    var pos_y = [_]u32{100, 100};
    var count: u32 = 0;
    var map: [200][200]u8 = undefined;
    var robot: u8 = 0;
    for (text) |c| {
        switch (c) {
            '>' => {
                pos_x[robot] += 1;
            },
            '<' => {
                pos_x[robot] -= 1;
            },
            '^' => {
                pos_y[robot] += 1;
            },
            'v' => {
                pos_y[robot] -= 1;
            },
            else => {},
        }
        if (map[pos_x[robot]][pos_y[robot]] != 'x') {
            map[pos_x[robot]][pos_y[robot]] = 'x';
            count += 1;
        }
        robot = (robot+1) % 2;
    }

    return count;
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
    var arg_exe = arg_it.next() orelse "";

    const filename = arg_it.next() orelse {
        std.debug.print("Please enter filename to input data:\n", .{});
        std.debug.print("> {s} <filename>\n", .{ arg_exe });
        return error.InvalidArgs;
    };

    const limit = 1 * 1024 * 1024 * 1024;
    const text = try fs.cwd().readFileAlloc(allocator, filename, limit);
    defer allocator.free(text);

    try stdout.print("AoC2015 - Day03\n===============\n", .{});
    try stdout.print("Total Houses for part 1: {d}\n", .{ part_1(text) });
    try stdout.print("Total Houses for part 2: {d}\n", .{ part_2(text) });
    try bw.flush(); // don't forget to flush!
}
