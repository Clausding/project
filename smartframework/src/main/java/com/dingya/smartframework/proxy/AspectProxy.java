package com.dingya.smartframework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author: dingya
 * @Description:切面基础类
 * @Date: Created in 10:25 2018/7/2
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    /**
     * 执行代理操作
     * @param proxyChain
     * @return
     * @throws Throwable
     */
    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        // 本方法返回值
        Object result = null;
        // 目标类
        Class<?> targetClass = proxyChain.getTargetClass();
        // 目标方法
        Method targetMethod = proxyChain.getTargetMethod();
        // 方法参数
        Object[] methodParams = proxyChain.getMethodParams();
        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
                // 执行代理链条
                result = proxyChain.doProxyChain();
                after(targetClass, targetMethod, methodParams, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        } catch (Exception e) {
            LOGGER.error("proxy failure", e);
            error(targetClass, targetMethod, methodParams, e);
            throw e;
        } finally {
            end();
        }
        return result;
    }

    public void begin() {
    }

    public boolean intercept(Class<?> targetClass, Method tartgetMethod, Object[] methodParams) throws Throwable {
        return true;
    }

    public void before(Class<?> targetClass, Method tartgetMethod, Object[] methodParams) throws Throwable {
        System.out.println("before in AspectProxy.java");
    }

    public void after(Class<?> targetClass, Method tartgetMethod, Object[] methodParams, Object result) throws Throwable {
        System.out.println("after in AspectProxy.java");
    }

    public void error(Class<?> targetClass, Method tartgetMethod, Object[] methodParams, Throwable e) throws Throwable {
    }

    public void end() {
    }
}