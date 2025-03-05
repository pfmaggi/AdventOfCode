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

    var it = std.mem.tokenizeSequence(u8, text, "\n\r\t");
    var buf: [16]u8 = undefined;
    var hash: [std.crypto.hash.Md5.digest_length]u8 = undefined;
    var number: u32 = 1;
    var nFiveZeros: u32 = 0;
    var nSixZeros: u32 = 0;
    while (it.next()) |line| {
        while (nFiveZeros == 0 or nSixZeros == 0) {
            const hash_input = std.fmt.bufPrint(&buf, "{s}{d}", .{ line, number }) catch unreachable;
            std.crypto.hash.Md5.hash(hash_input, &hash, .{});
            if (hash[0] == 0 and hash[1] == 0) {
                if (hash[2] & 0xF0 == 0 and nFiveZeros == 0) {
                    nFiveZeros = number;
                }
                if (hash[2] == 0 and nSixZeros == 0) {
                    nSixZeros = number;
                }
            }

            number += 1;
        }
    }

    try stdout.print("AoC2015 - Day04\n===============\n", .{});
    try stdout.print("Lowest possible number for 5 zeros hashes: {d}\n", .{nFiveZeros});
    try stdout.print("Lowest possible number for 6 zeros hashes: {d}\n", .{nSixZeros});
    try bw.flush(); // don't forget to flush!
}
