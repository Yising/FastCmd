package com.yising.fast.cmd.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CmdMatchUtilsTest {

    @Test
    void containersParam() {
        String cmdString = "adb shell bm stop {package}";
        assertEquals(true, CmdUtils.containersPlaceholder("{package}"));
    }

    @Test
    void replacePlaceholder() {
        String cmdString = "adb shell am start -W -a {action} -d \"{scheme}://{host}/detail?{funNumKey}={funNumValue}\" {packageName}";
        Map<String, String> params = new HashMap<>();
        params.put("packageName", "com.yising.fast");
        params.put("action", "android.intent.action.VIEW");
        params.put("scheme", "fastcmd");
        params.put("host", "com.yising.fast.cmd");
        params.put("funNumKey", "funNum");
        params.put("funNumValue", "5243");
        String cmd = CmdUtils.replacePlaceholder(cmdString, params);
        assertEquals("adb shell am start -W -a android.intent.action.VIEW -d \"fastcmd://com.yising.fast.cmd/detail?funNum=5243\" com.yising.fast", cmd);
    }

    @Test
    void getParamList() {
        String cmdString = "adb shell am start -W -a {action} -d \"{scheme}://{host}/detail?{funNumKey}={funNumValue}\" {packageName}";
        List<String> paramList = CmdUtils.getParamList(cmdString);
        assertEquals("action scheme host funNumKey funNumValue packageName", paramList.stream().collect(Collectors.joining(" ")));
    }
}