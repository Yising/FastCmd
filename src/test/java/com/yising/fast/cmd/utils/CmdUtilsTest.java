package com.yising.fast.cmd.utils;

import org.junit.jupiter.api.Test;

import java.io.IOException;

class CmdUtilsTest {
    @Test
    void execute() {
        String cmd = "adb devices &&echo 你打我啊&&ipconfig";
        CmdUtils.execute(cmd, true);
    }
}