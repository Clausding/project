package com.dingya.smartframework.helper;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: dingya
 * @Description:
 * @Date: Created in 9:29 2018/6/27
 */
public class ConfigHelperTest {

    @Test
    public void getJdbcDriver() {
        System.out.println(ConfigHelper.getJdbcDriver());
    }

    @Test
    public void getJdbcUrl() {
        System.out.println(ConfigHelper.getJdbcUrl());
    }

    @Test
    public void getJdbcUsername() {
        System.out.println(ConfigHelper.getJdbcUsername());
    }

    @Test
    public void getJdbcPassword() {
        System.out.println(ConfigHelper.getJdbcPassword());
    }

    @Test
    public void getAppBasePackage() {
        System.out.println(ConfigHelper.getAppBasePackage());
    }

    @Test
    public void getAppJspPath() {
        System.out.println(ConfigHelper.getAppJspPath());
    }

    @Test
    public void getAppAssertPath() {
        System.out.println(ConfigHelper.getAppAssertPath());
    }
}
