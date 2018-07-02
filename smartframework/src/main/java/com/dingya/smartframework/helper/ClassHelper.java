package com.dingya.smartframework.helper;

import com.dingya.smartframework.annotation.Controller;
import com.dingya.smartframework.annotation.Service;
import com.dingya.smartframework.util.ClassUtil;

import java.lang.annotation.Annotation;
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
     * 获得应用包名下，指定父类的子类，指定接口的实现类
     *
     * @param superClass 父类或接口
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getClassBySuper(Class<?> superClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls :
                CLASS_SET) {
            if (cls.isAssignableFrom(superClass) && !superClass.equals(cls)) {
                classSet.add(cls);
            }
        }
        return classSet;
    }

    /**
     * 获取应用包名下的，带有某注解的所有类
     *
     * @param annotationClass 注解类
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getClasssetByAnnotaion(Class<? extends Annotation> annotationClass) {
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        for (Class<?> cls :
                CLASS_SET) {
            if (cls.isAnnotationPresent(annotationClass)) {
                classSet.add(cls);
            }
        }
        return classSet;
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
        return getClasssetByAnnotaion(Controller.class);
    }

    /**
     * 获取应用包名下的，'@Service'注解的所有类
     *
     * @return 1个HashSet，作为类容器
     */
    public static Set<Class<?>> getServiceClassSet() {
        return getClasssetByAnnotaion(Service.class);
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
