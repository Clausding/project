package com.dingya.smartframework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: dingya
 * @Description:字符串工具类
 * @Date: Created in 11:01 2018/6/27
 */
public final class StringUtil {

    public static final String SEPERATOR = String.valueOf((char)29);

    /**
     * 字符串是否属于： null/空格/空字符串
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }
}
