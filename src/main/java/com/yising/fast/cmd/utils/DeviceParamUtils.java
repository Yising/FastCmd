package com.yising.fast.cmd.utils;

import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
        ClassPathResource classPathResource = new ClassPathResource(COMMON_PARAMS_FILE);
        try (InputStream is = classPathResource.getInputStream()) {
            String str = IoUtil.read(is, StandardCharsets.UTF_8);
            if (StringUtils.isEmpty(str)) {
                return Optional.empty();
            }
            return Optional.ofNullable(JSONObject.parseObject(str));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 读取设备对应的参数列表
     *
     * @param deviceId 设备类型id
     */
    private static Optional<JSONObject> getDeviceParams(int deviceId) {
        String str = StringUtils.empty();
        ClassPathResource classPathResource = new ClassPathResource(DEVICE_PARAMS_FILE);
        try (InputStream is = classPathResource.getInputStream()) {
            str = IoUtil.read(is, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
