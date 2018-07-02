package com.dingya.smartframework.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author: dingya
 * @Description:切面基础类，所有切面类都继承此类
 * @Date: Created in 10:25 2018/7/2
 */
public abstract class AspectProxy implements Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public final Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;
        Class<?> targetClass = proxyChain.getTargetClass();
        Method targetMethod = proxyChain.getTargetMethod();
        Object[] methodParams = proxyChain.getMethodParams();
        begin();
        try {
            if (intercept(targetClass, targetMethod, methodParams)) {
                before(targetClass, targetMethod, methodParams);
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
    }

    public void after(Class<?> targetClass, Method tartgetMethod, Object[] methodParams, Object result) throws Throwable {
    }

    public void error(Class<?> targetClass, Method tartgetMethod, Object[] methodParams, Throwable e) throws Throwable {
    }

    public void end() {
    }
}
