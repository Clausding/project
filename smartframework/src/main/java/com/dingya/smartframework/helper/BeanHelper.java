package com.dingya.smartframework.helper;


import com.dingya.smartframework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: dingya
 * @Description:bean助手类
 * @Date: Created in 9:10 2018/6/28
 */
public class BeanHelper {
    /**
     * bean容器
     */
    private static final Map<Class<?>, Object> BEAN_MAP = new HashMap<Class<?>, Object>();

    static {
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        for (Class<?> beanClass : beanClassSet) {
            Object obj = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, obj);
        }
    }

    /**
     * 获取单个bean
     *
     * @param beanClass 要获得的bean的类
     * @param <T>
     * @return 单个bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        if (!BEAN_MAP.containsKey(beanClass)) {
            throw new RuntimeException("can not get bean by class:" + beanClass);
        }
        return (T) BEAN_MAP.get(beanClass);
    }

    /**
     * 获取bean容器
     *
     * @return bean容器，1个HashMap
     */
    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

}
