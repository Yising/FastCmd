package com.yising.fast.cmd.service;

import com.alibaba.fastjson.JSONObject;
import com.yising.fast.cmd.pojo.entity.Cmd;

import java.util.List;
import java.util.Optional;

/**
 * cmd服务类
 *
 * @author yising
 */
public interface CmdService {

    /**
     * 查询所有命令
     *
     * @return 查询到的所有命令
     */
    List<Cmd> list();

    /**
     * 查询所有命令
     *
     * @param id 命令id
     * @return id对应的命令
     */
    Optional<Cmd> queryById(long id);

    /**
     * 添加命令
     *
     * @param cmd 命令
     * @return 对应的命令
     */
    Cmd add(Cmd cmd);

    /**
     * 将命令转储为json文件
     *
     * @return 是否成功
     */
    boolean saveToFile();

    /**
     * 根据设备id查询命令的默认参数
     *
     * @param cmdId 命令id
     * @param deviceId 设备id
     * @return 参数集合
     */
    Optional<JSONObject> queryDefaultParams(long cmdId, int deviceId);
}
