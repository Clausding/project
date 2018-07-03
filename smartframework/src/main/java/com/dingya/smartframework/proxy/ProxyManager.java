package com.dingya.smartframework.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author: dingya
 * @Description:代理管理器
 * @Date: Created in 10:15 2018/7/2
 */
@SuppressWarnings("unchecked")
public class ProxyManager {
    /**
     * 利用cglib制造一个代理对象
     *
     * @param targetClass 目标类
     * @param proxyList   代理对象列表
     * @param <T>
     * @return 代理对象
     */
    public static <T> T createProxy(final Class<?> targetClass, final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
            @Override
            public Object intercept(Object targetObject, Method targetMethod, Object[] methodParams, MethodProxy methodProxy) throws Throwable {
                return new ProxyChain(targetClass, targetObject, targetMethod, methodProxy, methodParams, proxyList).doProxyChain();
            }
        });
    }
}
