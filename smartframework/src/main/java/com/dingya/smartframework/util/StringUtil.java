package com.dingya.smartframework.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author: dingya
 * @Description:字符串工具类
 * @Date: Created in 11:01 2018/6/27
 */
public class StringUtil {

    /**
     * 判断字符串是 null 空格 空字符串 吗？
     *
     * @param str
     * @return 布尔值
     */
    public static boolean isEmpty(String str) {
        if (str != null) {
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }
}
