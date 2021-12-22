// /*
//  * Copyright 2021 Pietro F. Maggi
//  *
//  * Licensed under the Apache License, Version 2.0 (the "License");
//  * you may not use this file except in compliance with the License.
//  * You may obtain a copy of the License at
//  *
//  *     http://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.
//  */

const std = @import("std");

pub fn main() anyerror!void {
    var arena = std.heap.ArenaAllocator.init(std.heap.page_allocator);
    defer arena.deinit();
    const allocator = arena.allocator();
    const stdout = std.io.getStdOut().writer();

    const limit = 1 * 1024 * 1024 * 1024;
    const text = try std.fs.cwd().readFileAlloc(allocator, "../input/day01.txt", limit);

    var floor: i32 = 0;
    var position: usize = 0;
    for (text) |c, i| {
        if (c == '(') floor += 1;
        if (c == ')') floor -= 1;
        if ((0 == position) and (floor < 0)) position = i + 1;
    }

    try stdout.print("AoC2015 - Day01\n===============\n", .{});
    try stdout.print("Final floor is: {d}\n", .{floor});
    try stdout.print("Entered the basement at position: {d}\n", .{position});
}