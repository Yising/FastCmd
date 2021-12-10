package com.yising.fast.cmd.pojo.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * 页面请求响应封装
 *
 * @author yising
 */
@Getter
@Setter
public class ResponseBean<T> {

    private static final int SUCCESS_CODE = 0;

    private static final int FAIL_CODE = -1;

    private int code;

    private String msg;

    private T data;

    private ResponseBean(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResponseBean<T> success(String msg) {
        return success(msg, null);
    }

    public static <T> ResponseBean<T> success(T obj) {
        return success("", obj);
    }

    public static <T> ResponseBean<T> fail(String msg) {
        return fail(msg, null);
    }

    public static <T> ResponseBean<T> fail(T obj) {
        return fail("", obj);
    }

    public static <T> ResponseBean<T> success(String msg, T obj) {
        return new ResponseBean<T>(SUCCESS_CODE, msg, obj);
    }

    public static <T> ResponseBean<T> fail(String msg, T obj) {
        return new ResponseBean<T>(FAIL_CODE, msg, obj);
    }
}
