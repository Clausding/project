package com.dingya.smartframework.helper;

import com.dingya.smartframework.ConfigConstant;
import com.dingya.smartframework.util.PropsUtil;

import java.util.Properties;

/**
 * @Description:属性文件助手类
 * @Author: dingya
 * @Date: Created in 21:08 2018/6/26
 */
public class ConfigHelper {
    private static final Properties CONFIG_PROPS = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    /**
     * 获取 Jdbc 驱动
     *
     * @return
     */
    public static String getJdbcDriver() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_DRIVER);
    }

    /**
     * 获取 Jdbc URL
     *
     * @return
     */
    public static String getJdbcUrl() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_URL);
    }

    /**
     * 获取 Jdbc 用户名
     *
     * @return
     */
    public static String getJdbcUsername() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_USERNAME);
    }

    /**
     * 获取 Jdbc 密码
     *
     * @return
     */
    public static String getJdbcPassword() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.JDBC_PASSWORD);
    }

    /**
     * 获取应用基础包名
     *
     * @return
     */
    public static String getAppBasePackage() {
        return PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_BASE_PACKAGE);
    }

    /**
     * 获取应用JSP路径
     *
     * @return
     */
    public static String getAppJspPath() {
        String value = PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_JSP_PATH);
        if(value.length() == 0) {
            return "/WEB-INF/view/";
        }
        return value;
    }

    /**
     * 获取应用静态资源路径
     *
     * @return
     */
    public static String getAppAssertPath() {
        String value = PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_ASSET_PATH);
        if(value.length() == 0) {
            return "/asset/";
        }
        return value;
    }

    /**
     * 获取应用文件上传限制
     *
     * @return
     */
    public static int getAppUploadLimit() {
        String value = PropsUtil.getString(CONFIG_PROPS, ConfigConstant.APP_UPLOAD_LIMIT);
        if(value.length() == 0) {
            return 10;
        }
        return Integer.parseInt(value);
    }
}
