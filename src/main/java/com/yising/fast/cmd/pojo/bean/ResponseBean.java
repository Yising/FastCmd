package com.yising.fast.cmd.pojo.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseBean {

    private static final int SUCCESS_CODE = 0;

    private static final int FAIL_CODE = -1;

    private int code;

    private String msg;

    private Object data;

    private ResponseBean() {

    }

    public static ResponseBean success(String msg) {
        return success(msg, null);
    }

    public static ResponseBean success(Object obj) {
        return success("", obj);
    }

    public static ResponseBean fail(String msg) {
        return fail(msg, null);
    }

    public static ResponseBean fail(Object obj) {
        return fail("", obj);
    }

    public static ResponseBean success(String msg, Object obj) {
        return response(SUCCESS_CODE, msg, obj);
    }

    public static ResponseBean fail(String msg, Object obj) {
        return response(FAIL_CODE, msg, obj);
    }

    private static ResponseBean response(int code, String msg, Object obj) {
        ResponseBean bean = new ResponseBean();
        bean.msg = msg;
        bean.data = obj;
        bean.code = code;
        return bean;
    }
}
