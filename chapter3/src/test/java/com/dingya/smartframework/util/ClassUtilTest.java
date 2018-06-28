package com.dingya.smartframework.util;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;


/**
 * @Author: dingya
 * @Description:类操作工具类测试
 * @Date: Created in 14:40 2018/6/27
 */
public class ClassUtilTest {

    @Test
    public void getClassSet() {
        Set<Class<?>> classSet = ClassUtil.getClassSet("com.dingya.smartframework.util");
        Iterator<Class<?>> iterator = classSet.iterator();
        while (iterator.hasNext()) {
            System.out.println("类名：" + iterator.next());
        }
    }
}