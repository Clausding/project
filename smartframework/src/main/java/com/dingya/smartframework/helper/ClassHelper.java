package com.dingya.smartframework.helper;

import com.dingya.smartframework.annotation.Controller;
import com.dingya.smartframework.annotation.Service;
import com.dingya.smartframework.util.ClassUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author: dingya
 * @Description:类操作助手类
 * @Date: Created in 15:49 2018/6/27
 */
public class ClassHelper {
    /**
     * 类容器
     */
    private static final Set<Class<?>> CLASS_SET;

    static {
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取应用包名下的，'@Controller'或@Service'注解的所有类
     *
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getBeanClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        classSet.addAll(getServiceClassSet());
        classSet.addAll(getControllerClassSet());
        return classSet;
    }

    /**
     * 获取应用包名下的，'@Controller'注解的所有类
     *
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getControllerClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls :
                CLASS_SET) {
            if (cls.isAnnotationPresent(Controller.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的，'@Service'注解的所有类
     *
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getServiceClassSet() {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls :
                CLASS_SET) {
            if (cls.isAnnotationPresent(Service.class)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的所有类
     *
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getClassSet() {
        return CLASS_SET;
    }
}
