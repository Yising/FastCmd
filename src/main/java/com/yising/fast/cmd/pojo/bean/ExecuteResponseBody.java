package com.yising.fast.cmd.pojo.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * cmd执行结果响应类
 *
 * @author yising
 */
@Getter
@Setter
@AllArgsConstructor
public class ExecuteResponseBody {
    private String cmdStr;

    private String outputStr;
}
