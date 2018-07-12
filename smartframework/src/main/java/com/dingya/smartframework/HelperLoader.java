package com.dingya.smartframework;

import com.dingya.smartframework.helper.*;
import com.dingya.smartframework.util.ClassUtil;

/**
 * @Author: dingya
 * @Description:统一加载'com.dingya.smartframework.helper'目录下的类
 * @Date: Created in 15:08 2018/6/28
 */
public class HelperLoader {

    /**
     * bean容器 / 依赖注入 / 请求和处理器关系 的初始化
     */
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
