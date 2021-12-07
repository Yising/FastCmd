package com.yising.fast.cmd.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yising.fast.cmd.pojo.entity.Cmd;
import com.yising.fast.cmd.repository.CmdRepository;
import com.yising.fast.cmd.service.CmdService;
import com.yising.fast.cmd.utils.CmdUtils;
import com.yising.fast.cmd.utils.DeviceParamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * 命令实现类
 *
 * @author yising
 */
@Slf4j
@Service
public class CmdServiceImpl implements CmdService {

    @Resource
    private CmdRepository cmdRepository;

    @Override
    public List<Cmd> list() {
        return cmdRepository.findAll(Sort.by(Sort.Direction.DESC, "weight"));
    }

    @Override
    public Optional<Cmd> queryById(long id) {
        return cmdRepository.findById(id);
    }

    @Override
    public Cmd add(Cmd cmd) {
        return cmdRepository.save(cmd);
    }

    @Override
    public boolean saveToFile() {
        List<Cmd> cmdList = cmdRepository.findAll();
        String jsonString = JSONArray.toJSONString(cmdList);
        String fileName = System.currentTimeMillis() + ".json";
        BufferedWriter writer = null;
        try {
            log.info("file name is:" + fileName);
            writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(jsonString);
        } catch (IOException e) {
            log.info("write fail");
            return false;
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                log.info("write fail");
            }
        }
        return true;
    }

    @Override
    public Optional<JSONObject> queryDefaultParams(long cmdId, int deviceId) {
        Optional<Cmd> cmdOpt = cmdRepository.findById(cmdId);
        if (!cmdOpt.isPresent()) {
            log.warn("cmd id: {}, not exist", cmdId);
            return Optional.empty();
        }
        Cmd cmd = cmdOpt.get();
        Optional<JSONObject> allDefaultParamsOpt = DeviceParamUtils.getAllDefaultParams(deviceId);
        if (!allDefaultParamsOpt.isPresent() || !CmdUtils.containersPlaceholder(cmd.getCmd())) {
            log.warn("cmd id: {},not need params or no params for device id: {}", cmdId, deviceId);
            return Optional.empty();
        }
        JSONObject allDefaultParams = allDefaultParamsOpt.get();
        JSONObject jsonObject = new JSONObject();
        CmdUtils.getParamList(cmd.getCmd()).forEach(paramKey ->
            jsonObject.put(paramKey, allDefaultParams.getOrDefault(paramKey, "no default value, please enter")));
        return Optional.of(jsonObject);
    }
}
