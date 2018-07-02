package com.dingya.smartframework.proxy;

import com.dingya.smartframework.annotation.Aspect;
import com.dingya.smartframework.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Author: dingya
 * @Description:拦截Controller所有的方法
 * @Date: Created in 11:23 2018/7/2
 */
@Aspect(Controller.class)
public class ControllerAspect extends AspectProxy{
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    private long begin;

    @Override
    public void before(Class<?> targetClass, Method tartgetMethod, Object[] methodParams) throws Throwable {
        LOGGER.debug("------------begin-----------");
        LOGGER.debug(String.format("class: %s", targetClass.getName()));
        LOGGER.debug(String.format("method: %s", tartgetMethod.getName()));
        begin = System.currentTimeMillis();
    }

    @Override
    public void after(Class<?> targetClass, Method tartgetMethod, Object[] methodParams, Object result) throws Throwable {
        LOGGER.debug(String.format("time: %ds", System.currentTimeMillis() - begin));
        LOGGER.debug("------------end-----------");
    }
}
