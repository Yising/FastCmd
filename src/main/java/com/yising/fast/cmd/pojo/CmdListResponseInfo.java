package com.yising.fast.cmd.pojo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * CMD查询结果响应类
 * */
@Getter
@Setter
public class CmdListResponseInfo {
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
