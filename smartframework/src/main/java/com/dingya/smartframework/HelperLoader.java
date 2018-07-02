package com.dingya.smartframework;

import com.dingya.smartframework.annotation.Controller;
import com.dingya.smartframework.helper.*;
import com.dingya.smartframework.util.ClassUtil;

/**
 * @Author: dingya
 * @Description:统一加载'com.dingya.smartframework.helper'目录下的类
 * @Date: Created in 15:08 2018/6/28
 */
public class HelperLoader {

    public static void init() {
        Class<?> [] classes = {
                ClassHelper.class,
                BeanHelper.class,
                AopHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (int i = 0; i < classes.length; i++) {
            ClassUtil.loadClass(classes[i].getName(), true);
        }
    }
}
