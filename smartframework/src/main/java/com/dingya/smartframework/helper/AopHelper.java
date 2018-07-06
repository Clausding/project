package com.dingya.smartframework.helper;

import com.dingya.smartframework.annotation.Aspect;
import com.dingya.smartframework.proxy.AspectProxy;
import com.dingya.smartframework.proxy.Proxy;
import com.dingya.smartframework.proxy.ProxyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @Author: dingya
 * @Description:AOP助手类
 * @Date: Created in 14:22 2018/7/2
 */
public class AopHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AopHelper.class);

    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            // 将bean容器中目标类对象替换成代理对象
            for (Map.Entry<Class<?>, List<Proxy>> entry : targetMap.entrySet()) {
                Class<?> targetClass = entry.getKey();
                List<Proxy> proxyList = entry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHelper.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop failure", e);
        }
    }

    /**
     * 获得映射关系（目标类：代理对象集合）
     *
     * @param proxyMap 映射关系（代理类：目标类集合）
     * @return 映射关系（目标类：代理对象集合）
     * @throws Exception
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws Exception {
        // 关系映射容器（目标类：代理对象）
        Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> entry :
                proxyMap.entrySet()) {
            Class<?> proxyClass = entry.getKey();
            Set<Class<?>> targetClasses = entry.getValue();
            for (Class<?> targetClass : targetClasses) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey(targetClass)) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    List<Proxy> proxyList = new LinkedList<Proxy>();
                    proxyList.add(proxy);
                    targetMap.put(targetClass, proxyList);
                }
            }
        }
        return targetMap;
    }


    /**
     * 获得映射关系（代理类：目标类集合）
     *
     * @return 关系映射容器（代理类：目标类集合）
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() {
        // 关系映射容器（代理类：目标类集合）
        Map<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        // AspectProxy的子类集合
        Set<Class<?>> proxyClassSet = ClassHelper.getClassBySuper(AspectProxy.class);
        for (Class<?> proxyClass :
                proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                // 切面类定义的切点
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
        return proxyMap;
    }

    /**
     * '@Aspect()'注解带有的值也是一个注解，找出后一注解应用的所有类
     *
     * @param aspect 注解对象
     * @return 类容器
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) {
        Set<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClasssetByAnnotaion(annotation));
        }
        return targetClassSet;
    }
}
