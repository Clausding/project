package com.dingya.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:属性文件工具类
 * @Author: dingya
 * @Date: Created in 21:11 2018/6/26
 */
public class PropsUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    /**
     * 获取String类型的属性值
     *
     * @param props Properties类型对象
     * @param key   键
     * @return 属性值
     */
    public static String getString(Properties props, String key) {
        String value = "";
        if (props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 加载属性文件
     *
     * @param filePath 属性文件路径
     * @return
     */
    public static Properties loadProps(String filePath) {
        Properties props = null;
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
            if (is == null) {
                throw new FileNotFoundException(filePath + " file is not found");
            }
            props = new Properties();
            props.load(is);
        } catch (IOException e) {
            LOGGER.error("load properties file failure" + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close inputstream failure" + e);
                }
            }
        }
        return props;
    }

}
