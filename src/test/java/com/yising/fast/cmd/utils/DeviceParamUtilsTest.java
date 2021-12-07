package com.yising.fast.cmd.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Optional;


@Slf4j
class DeviceParamUtilsTest {

    private static final int DEFAULT_DEVICE_ID = 1;

    @Test
    void getDefaultParams() {
        Optional<JSONObject> opt = DeviceParamUtils.getAllDefaultParams(DEFAULT_DEVICE_ID);
        if (!opt.isPresent()) {
            log.info("find no device id: " + DEFAULT_DEVICE_ID);
            return;
        }
        log.info(opt.get().toJSONString());
    }
}