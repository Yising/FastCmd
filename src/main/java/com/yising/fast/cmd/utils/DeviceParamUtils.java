package com.yising.fast.cmd.utils;

import cn.hutool.core.io.file.FileReader;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Optional;


/**
 * 配置解析工具类
 *
 * @author yising
 */
public class DeviceParamUtils {
    private static final String DEVICE_PARAMS_FILE = "device_params.json";

    private static final String COMMON_PARAMS_FILE = "common_params.json";

    private static final String KEY_DEVICE_PARAM_ID = "id";

    /**
     * 读取设备 + 通用参数列表
     *
     * @param deviceId 设备id
     */
    public static Optional<JSONObject> getAllDefaultParams(int deviceId) {
        Optional<JSONObject> commonParamsOpt = getCommonParams();
        Optional<JSONObject> deviceParamsOpt = getDeviceParams(deviceId);
        if (!commonParamsOpt.isPresent() && !deviceParamsOpt.isPresent()) {
            return Optional.empty();
        }
        JSONObject allParams = new JSONObject();
        commonParamsOpt.ifPresent(allParams::putAll);
        deviceParamsOpt.ifPresent(allParams::putAll);
        return Optional.of(allParams);
    }

    /**
     * 读取设备通用参数列表
     */
    private static Optional<JSONObject> getCommonParams() {
        FileReader fileReader = new FileReader(COMMON_PARAMS_FILE);
        String str = fileReader.readString();
        if (StringUtils.isEmpty(str)) {
            return Optional.empty();
        }
        return Optional.ofNullable(JSONObject.parseObject(str));
    }

    /**
     * 读取设备对应的参数列表
     *
     * @param deviceId 设备类型id
     */
    private static Optional<JSONObject> getDeviceParams(int deviceId) {
        FileReader fileReader = new FileReader(DEVICE_PARAMS_FILE);
        String str = fileReader.readString();
        if (StringUtils.isEmpty(str)) {
            return Optional.empty();
        }
        JSONArray jsonArray = JSONArray.parseArray(str);
        if (jsonArray == null) {
            return Optional.empty();
        }
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (jsonObject == null || !jsonObject.containsKey(KEY_DEVICE_PARAM_ID)) {
                continue;
            }
            int id = jsonObject.getIntValue(KEY_DEVICE_PARAM_ID);
            if (id == deviceId) {
                return Optional.of(jsonObject);
            }
        }
        return Optional.empty();
    }
}
