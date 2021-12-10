package com.yising.fast.cmd.controller;

import com.alibaba.fastjson.JSONObject;
import com.yising.fast.cmd.common.MsgConst;
import com.yising.fast.cmd.pojo.bean.CmdListResponseBody;
import com.yising.fast.cmd.pojo.bean.ExecuteRequestParam;
import com.yising.fast.cmd.pojo.bean.ExecuteResponseBody;
import com.yising.fast.cmd.pojo.bean.QueryRequestParam;
import com.yising.fast.cmd.pojo.bean.ResponseBean;
import com.yising.fast.cmd.pojo.converter.CmdEntity2CmdRespBody;
import com.yising.fast.cmd.pojo.entity.Cmd;
import com.yising.fast.cmd.service.CmdService;
import com.yising.fast.cmd.utils.CmdUtils;
import com.yising.fast.cmd.utils.CollectionUtils;
import com.yising.fast.cmd.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 首页控制类
 *
 * @author yising
 */
@RestController
@Slf4j
@CrossOrigin(value = "http://localhost:8080")
public class MainController {
    @Autowired
    private CmdService cmdService;

    @RequestMapping("/test")
    @ResponseBody
    public ResponseBean test() {
        String cmd = "ipconfig";
        String cmdResult = CmdUtils.execute(cmd, false);
        log.info(cmdResult);
        Cmd cmdEntity = new Cmd();
        cmdEntity.setName("adb devices");
        cmdEntity.setCmd("adb devices");
        cmdService.add(cmdEntity);
        return ResponseBean.success(new ExecuteResponseBody(cmd, cmdResult));
    }
    @RequestMapping("/list")
    @ResponseBody
    public ResponseBean list() {
        CmdListResponseBody cmdListResponseBody = new CmdEntity2CmdRespBody().apply(cmdService.list());
        return ResponseBean.success(cmdListResponseBody);
    }

    @RequestMapping("/query")
    @ResponseBody
    public ResponseBean query(QueryRequestParam queryParams) {
        List<Cmd> cmdList = cmdService.list();
        if (CollectionUtils.isEmpty(cmdList)) {
            ResponseBean.fail("no data to show");
        }
        String type = queryParams.getType();
        if (!StringUtils.isEmpty(type)) {
            return ResponseBean.success(cmdList.stream().filter(cmd -> type.equals(cmd.getType())).collect(Collectors.toList()));
        }
        return ResponseBean.success(cmdService.list());
    }

    @PostMapping("/execute")
    @ResponseBody
    public ResponseBean execute(@RequestBody ExecuteRequestParam params) {
        if (params == null) {
            return ResponseBean.fail("params is null");
        }
        // 直接执行用户输入的cmd
        if (!StringUtils.isEmpty(params.getRawCmdString())) {
            String rawCmd = params.getRawCmdString();
            String result = CmdUtils.execute(rawCmd, params.isOnNewWindow());
            return ResponseBean.success(new ExecuteResponseBody(rawCmd, result));
        }
        log.info("cmd params is:" + JSONObject.toJSONString(params));
        Optional<Cmd> cmdOpt = cmdService.queryById(params.getCmdId());
        if (!cmdOpt.isPresent()) {
            return ResponseBean.fail("cmd not found.");
        }
        Cmd cmd = cmdOpt.get();
        String executeCmd = CmdUtils.replacePlaceholder(cmd.getCmd(), params.getParamMap());
        if (CmdUtils.containersPlaceholder(executeCmd)) {
            return ResponseBean.fail("not enough params, after replace: " + executeCmd);
        }
        String result = CmdUtils.execute(executeCmd, params.isOnNewWindow());
        return ResponseBean.success(new ExecuteResponseBody(executeCmd, result));
    }

    @RequestMapping("/queryParams")
    @ResponseBody
    public ResponseBean queryParams(long cmdId, int deviceId) {
        Optional<JSONObject> jsonObjectOpt = cmdService.queryDefaultParams(cmdId, deviceId);
        if (!jsonObjectOpt.isPresent()) {
            return ResponseBean.fail(MsgConst.QUERY_PARAMS_FAIL);
        }
        JSONObject jsonObject = jsonObjectOpt.get();
        jsonObject.put("cmdId", cmdId);
        jsonObject.put("deviceId", deviceId);
        return ResponseBean.success(jsonObject);
    }

    @RequestMapping("/saveJson")
    @ResponseBody
    public ResponseBean saveJson() {
        if (cmdService.saveToFile()) {
            return ResponseBean.success("save success");
        }
        return ResponseBean.fail("save success");
    }
}
