package com.dingya.smartframework.helper;

import com.dingya.smartframework.util.PrintUtil;
import org.junit.Test;

import java.util.Set;

/**
 * @Author: dingya
 * @Description:
 * @Date: Created in 16:14 2018/6/27
 */
public class ClassHelperTest {

    @Test
    public void getBeanClassSet() {
        Set<Class<?>> classSet = ClassHelper.getBeanClassSet();
        PrintUtil.printSet(classSet);
    }

    @Test
    public void getControllerClassSet() {
        Set<Class<?>> classSet = ClassHelper.getControllerClassSet();
        PrintUtil.printSet(classSet);
    }

    @Test
    public void getServiceClassSet() {
        Set<Class<?>> classSet = ClassHelper.getServiceClassSet();
        PrintUtil.printSet(classSet);
    }

    @Test
    public void getClassSet() {
        Set<Class<?>> classSet = ClassHelper.getClassSet();
        PrintUtil.printSet(classSet);
    }
}