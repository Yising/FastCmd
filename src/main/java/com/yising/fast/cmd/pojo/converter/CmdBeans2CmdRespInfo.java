package com.yising.fast.cmd.pojo.converter;

import com.yising.fast.cmd.pojo.CmdListResponseInfo;
import com.yising.fast.cmd.pojo.entity.Cmd;
import com.yising.fast.cmd.utils.CmdUtils;
import com.yising.fast.cmd.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CmdBeans2CmdRespInfo implements Function<List<Cmd>, CmdListResponseInfo> {
    @Override
    public CmdListResponseInfo apply(List<Cmd> cmdList) {
        CmdListResponseInfo responseInfo = new CmdListResponseInfo();
        if (CollectionUtils.isEmpty(cmdList)) {
            return responseInfo;
        }
        responseInfo.setCmdInfoList(new ArrayList<>());
        List<CmdListResponseInfo.CmdInfo> cmdInfoList = new ArrayList<>();
        cmdList.forEach(cmd -> {
            CmdListResponseInfo.CmdInfo cmdInfo = new CmdListResponseInfo.CmdInfo();
            cmdInfo.setId(cmd.getId());
            cmdInfo.setName(cmd.getName());
            cmdInfo.setShortCmd(cmd.getShortCmd());
            cmdInfo.setParams(CmdUtils.getParamList(cmd.getCmd()));
            cmdInfoList.add(cmdInfo);
        });
        responseInfo.setCmdInfoList(cmdInfoList);
        return responseInfo;
    }
}
