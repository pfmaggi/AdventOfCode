const std = @import("std");
const Step = @import("std").build.Step;

pub fn build(b: *std.build.Builder) void {
    // Standard target options allows the person running `zig build` to choose
    // what target to build for. Here we do not override the defaults, which
    // means any target is allowed, and the default is native. Other options
    // for restricting supported target set are available.
    const target = b.standardTargetOptions(.{});

    // Standard release options allow the person running `zig build` to select
    // between Debug, ReleaseSafe, ReleaseFast, and ReleaseSmall.
    const mode = b.standardReleaseOptions();
    const DAYS = 1;

    var run_day_step: [DAYS]*Step = undefined;
    var i: u8 = 1;
    while (i <= DAYS) : (i += 1) {
        const path = b.fmt("src/day{d:0>2}.zig", .{ i });
        const name = b.fmt("day{d:0>2}", .{ i });

        const exe = b.addExecutable(name, path);
        exe.setTarget(target);
        exe.setBuildMode(mode);
        // exe.addOptions("build_options", exe_options); // XXX Does not apply to package tools / 'tracy'.  no idea how to make it work

        exe.install();
        const installstep = &b.addInstallArtifact(exe).step;

        const run_cmd = exe.run();
        run_cmd.step.dependOn(installstep);

        const args = b.fmt("../input/day{d:0>2}.txt", .{ i });
        run_cmd.step.dependOn(b.getInstallStep());
        run_cmd.addArg(args);

        const runs_ext = b.fmt("Run day {d:0>2}", . { i });
        const runs = b.fmt("run_day{d:0>2}", . { i });
        run_day_step[i-1] = b.step(runs, runs_ext);
        run_day_step[i-1].dependOn(&run_cmd.step);
    }

    // const exe_tests = b.addTest("src/main.zig");
    // exe_tests.setTarget(target);
    // exe_tests.setBuildMode(mode);

    // const test_step = b.step("test", "Run unit tests");
    // test_step.dependOn(&exe_tests.step);
}
