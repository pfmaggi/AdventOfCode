const std = @import("std");

pub fn build(b: *std.Build) void {
    // Standard target options allows the person running `zig build` to choose
    // what target to build for. Here we do not override the defaults, which
    // means any target is allowed, and the default is native. Other options
    // for restricting supported target set are available.
    const target = b.standardTargetOptions(.{});

    // Standard optimization options allow the person running `zig build` to select
    // between Debug, ReleaseSafe, ReleaseFast, and ReleaseSmall. Here we do not
    // set a preferred release mode, allowing the user to decide how to optimize.
    const optimize = b.standardOptimizeOption(.{});
    const DAYS = 3;

    var run_day_step: [DAYS]*std.Build.Step = undefined;
    for (1..(DAYS+1)) |i| {
        const path = b.fmt("src/day{d:0>2}.zig", .{ i });
        const name = b.fmt("day{d:0>2}", .{ i });

        const exe = b.addExecutable(.{
            .name = name,
            .root_source_file = .{ .path = path },
            .target = target,
            .optimize = optimize,
        });

        // This declares intent for the executable to be installed into the
        // standard location when the user invokes the "install" step (the default
        // step when running `zig build`).
        b.installArtifact(exe);

        // This *creates* a Run step in the build graph, to be executed when another
        // step is evaluated that depends on it. The next line below will establish
        // such a dependency.
        const run_cmd = b.addRunArtifact(exe);

        // By making the run step depend on the install step, it will be run from the
        // installation directory rather than directly from within the cache directory.
        // This is not necessary, however, if the application depends on other installed
        // files, this ensures they will be present and in the expected location.
        run_cmd.step.dependOn(b.getInstallStep());

        const args = b.fmt("../input/day{d:0>2}.txt", .{ i });
        run_cmd.addArg(args);

        // This creates a build step. It will be visible in the `zig build --help` menu,
        // and can be selected like this: `zig build run`
        // This will evaluate the `run` step rather than the default, which is "install".
        // run_day_step[i-1] = b.step("run", "Run the app");
        // run_day_step[i-1].dependOn(&run_cmd.step);

        const runs_ext = b.fmt("Run day {d:0>2}", . { i });
        const runs = b.fmt("run_day{d:0>2}", . { i });
        run_day_step[i-1] = b.step(runs, runs_ext);
        run_day_step[i-1].dependOn(&run_cmd.step);
    }
}
