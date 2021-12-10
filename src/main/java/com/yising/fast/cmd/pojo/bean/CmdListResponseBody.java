package com.yising.fast.cmd.pojo.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * CMD查询结果响应类
 *
 * @author yising
 */
@Getter
@Setter
public class CmdListResponseBody {
    List<CmdInfo> cmdInfoList;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class CmdInfo {
        private long id;
        private String name;
        private String shortCmd;
        private List<String> params;
    }
}
