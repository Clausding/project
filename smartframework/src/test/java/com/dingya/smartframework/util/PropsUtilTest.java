package com.dingya.smartframework.util;

import org.junit.Test;

import java.util.Properties;

/**
 * @Description:属性文件工具类测试方法
 * @Author: dingya
 * @Date: Created in 0:19 2018/6/27
 */
public class PropsUtilTest {
    @Test
    public void test() {
        Properties props = PropsUtil.loadProps("test.properties");
        String str = PropsUtil.getString(props, "test1");
        System.out.println(str);
    }
}
