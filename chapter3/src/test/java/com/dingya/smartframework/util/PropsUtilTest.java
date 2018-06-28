package com.dingya.smartframework.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

/**
 * @Description:
 * @Author: dingya
 * @Date: Created in 21:40 2018/6/26
 */
public class PropsUtilTest {

    @Test
    public void test() {
        Properties props = PropsUtil.loadProps("smart.properties");
        String value = PropsUtil.getString(props,"smart.framework.jdbc.driver");
        Assert.assertEquals("com.mysql.jdbc.Driver",value);
    }
}
