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
        std.debug.print("Please enter filename to input data:\n", .{});
        std.debug.print("> {s} <filename>\n", .{arg_exe});
        return error.InvalidArgs;
    };

    const limit = 1 * 1024 * 1024 * 1024;
    const text = try fs.cwd().readFileAlloc(allocator, filename, limit);
    defer allocator.free(text);

    var floor: i32 = 0;
    var position: usize = 0;
    for (text, 0..) |c, i| {
        if (c == '(') floor += 1;
        if (c == ')') floor -= 1;
        if ((0 == position) and (floor < 0)) position = i + 1;
    }

    try stdout.print("AoC2015 - Day01\n===============\n", .{});
    try stdout.print("Final floor is: {d}\n", .{floor});
    try stdout.print("Entered the basement at position: {d}\n", .{position});
    try bw.flush(); // don't forget to flush!
}
