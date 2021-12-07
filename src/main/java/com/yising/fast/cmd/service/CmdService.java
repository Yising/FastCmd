package com.yising.fast.cmd.service;

import com.alibaba.fastjson.JSONObject;
import com.yising.fast.cmd.pojo.entity.Cmd;

import java.util.List;
import java.util.Optional;

public interface CmdService {
    List<Cmd> list();

    Optional<Cmd> queryById(long id);

    Cmd add(Cmd cmd);

    boolean saveToFile();

    Optional<JSONObject> queryDefaultParams(long cmdId, int deviceId);
}
