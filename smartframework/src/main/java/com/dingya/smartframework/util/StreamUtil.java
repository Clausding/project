package com.dingya.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Author: dingya
 * @Description:流操作工具类
 * @Date: Created in 9:54 2018/6/29
 */
public final class StreamUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String getString(InputStream is) {
        StringBuffer sb = new StringBuffer();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error("get String from inputStream failure" + e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
