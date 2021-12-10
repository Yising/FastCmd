package com.yising.fast.cmd.utils;

/**
 * 字符串工具类
 *
 * @author yising
 */
public class StringUtils {
    private static final String EMPTY_STRING = "";

    private StringUtils() {
    }

    /**
     * 判断是否是空字符串
     * @param str 字符串
     * @return 是否为空字符串
     * */
    public static boolean isEmpty(final String str) {
        return str == null || str.equals(EMPTY_STRING);
    }

    /**
     * 获取空字符串
     *
     * @return 空字符串
     * */
    public static String empty() {
        return EMPTY_STRING;
    }
}
