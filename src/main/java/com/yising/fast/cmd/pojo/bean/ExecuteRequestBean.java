package com.yising.fast.cmd.pojo.bean;

import lombok.Data;

import java.util.Map;

@Data
public class ExecuteRequestBean {
    private long cmdId;

    private String rawCmdString;

    private boolean onNewWindow;

    private Map<String, String> paramMap;
}
