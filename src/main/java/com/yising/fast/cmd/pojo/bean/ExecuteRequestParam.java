package com.yising.fast.cmd.pojo.bean;

import lombok.Data;

import java.util.Map;

/**
 * 命令执行请求参数类
 *
 * @author yising
 */
@Data
public class ExecuteRequestParam {
    private long cmdId;

    private String rawCmdString;

    private boolean onNewWindow;

    private Map<String, String> paramMap;
}
