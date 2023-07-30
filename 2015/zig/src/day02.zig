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

fn min3(a: anytype, b: anytype, c: anytype) u32 {
    if (a < b) {
        return if (a < c) a else c;
    } else {
        return if (b < c) b else c;
    }
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

    var paper: u32 = 0;
    var ribbon: u32 = 0;
    var it = std.mem.split(u8, text, "\n");
    while (it.next()) |line| {
        if (line.len == 0)
            continue;
        var dim = std.mem.tokenize(u8, line, "x");
        var dims = [_]u32{ 0, 0, 0 };
        var i: u32 = 0;
        while (dim.next()) |d| {
            const trimmed = std.mem.trim(u8, d, " \n\r\t");
            dims[i] = try std.fmt.parseInt(u32, trimmed, 10);
            i += 1;
        }
        const l = dims[0];
        const w = dims[1];
        const h = dims[2];

        const volume = w * l * h;

        const face1 = l * w;
        const face2 = l * h;
        const face3 = w * h;
        const smallface = min3(face1, face2, face3);

        const perimeter1 = 2 * (l + w);
        const perimeter2 = 2 * (l + h);
        const perimeter3 = 2 * (w + h);
        const smallperimeter = min3(perimeter1, perimeter2, perimeter3);

        paper += 2 * (face1 + face2 + face3) + smallface;
        ribbon += smallperimeter + volume;
    }

    try stdout.print("AoC2015 - Day02\n===============\n", .{});
    try stdout.print("total paper = {d}\n", .{ paper });
    try stdout.print("total ribbon = {d}\n", .{ ribbon });
    try bw.flush(); // don't forget to flush!
}
