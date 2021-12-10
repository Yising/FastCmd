package com.yising.fast.cmd.pojo.converter;

import com.yising.fast.cmd.pojo.bean.CmdListResponseBody;
import com.yising.fast.cmd.pojo.entity.Cmd;
import com.yising.fast.cmd.utils.CmdUtils;
import com.yising.fast.cmd.utils.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * cmd实体类转换成cmd列表响应类
 *
 * @author yising
 */
public class CmdEntity2CmdRespBody implements Function<List<Cmd>, CmdListResponseBody> {
    @Override
    public CmdListResponseBody apply(List<Cmd> cmdList) {
        CmdListResponseBody responseInfo = new CmdListResponseBody();
        if (CollectionUtils.isEmpty(cmdList)) {
            return responseInfo;
        }
        responseInfo.setCmdInfoList(new ArrayList<>());
        List<CmdListResponseBody.CmdInfo> cmdInfoList = new ArrayList<>();
        cmdList.forEach(cmd -> {
            CmdListResponseBody.CmdInfo cmdInfo = new CmdListResponseBody.CmdInfo();
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
