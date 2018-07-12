package com.dingya.smartframework.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @Author: dingya
 * @Description:文件工具类
 * @Date: Created in 10:33 2018/7/11
 */
public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 创建文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File createFile(String filePath) {
        File result;
        try {
            result = new File(filePath);
            File parentFile = result.getParentFile();
            if (!parentFile.exists()) {
                FileUtils.forceMkdir(parentFile);
            }
        } catch (IOException e) {
            LOGGER.error("create file failure", e);
            throw new RuntimeException();
        }
        return result;
    }
}
