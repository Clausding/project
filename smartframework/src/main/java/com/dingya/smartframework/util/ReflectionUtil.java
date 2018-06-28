package com.dingya.smartframework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Author: dingya
 * @Description:反射工具类
 * @Date: Created in 16:34 2018/6/27
 */
public class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 通过反射设置成员变量
     *
     * @param obj   成员变量所在对象
     * @param field 成员变量对象
     * @param value 要设置的值
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field failure" + e);
            throw new RuntimeException();
        }
    }

    /**
     * 通过反射调用方法
     *
     * @param obj    方法所在对象
     * @param method 方法对象
     * @param args   参数列表
     * @return 被调用方法的返回值，如果方法返回类型为void,返回null
     */
    public static Object invokeMethod(Object obj, Method method, Object... args) {
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(obj, args);
        } catch (Exception e) {
            LOGGER.error("invoke method failure" + e);
            throw new RuntimeException();
        }
        return result;
    }

    /**
     * 通过反射创建对象
     *
     * @param cls 类
     * @return 创建的对象
     */
    public static Object newInstance(Class<?> cls) {
        Object obj;
        try {
            obj = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("new instance failure" + e);
            throw new RuntimeException();
        }
        return obj;
    }

}
