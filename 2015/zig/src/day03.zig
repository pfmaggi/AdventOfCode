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

const Coord = struct {
    x: i32,
    y: i32,
};

fn part_1(text: []u8) u32 {
    var map = std.AutoArrayHashMap(Coord, bool).init(std.heap.page_allocator);
    defer map.deinit();

    var count: u32 = 0;
    var x:i32 = 0;
    var y:i32 = 0;
    for (text) |c| {
        switch (c) {
            '>' => {
                x += 1;
            },
            '<' => {
                x -= 1;
            },
            '^' => {
                y += 1;
            },
            'v' => {
                y -= 1;
            },
            else => {},
        }

        const home = Coord {
            .x = x,
            .y = y,
        };
        if (!map.contains(home)) {
            map.put(home, true) catch {};
            count += 1;
        }
    }

    return count;
}

fn part_2(text: []u8) u32 {
    var map = std.AutoArrayHashMap(Coord, bool).init(std.heap.page_allocator);
    defer map.deinit();

    var x = [_]i32{0, 0};
    var y = [_]i32{0, 0};
    var count: u32 = 0;
    var robot: u8 = 0;
    for (text) |c| {
        switch (c) {
            '>' => {
                x[robot] += 1;
            },
            '<' => {
                x[robot] -= 1;
            },
            '^' => {
                y[robot] += 1;
            },
            'v' => {
                y[robot] -= 1;
            },
            else => {},
        }
        const home = Coord {
            .x = x[robot],
            .y = y[robot],
        };
        if (!map.contains(home)) {
            map.put(home, true) catch {};
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
