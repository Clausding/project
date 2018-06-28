package com.dingya.smartframework.bean;

import java.lang.reflect.Method;

/**
 * @Author: dingya
 * @Description:封装Action信息
 * @Date: Created in 11:14 2018/6/28
 */
public class Handler {
    /**
     * Controller类
     */
    private Class<?> controllerClass;

    /**
     * Action方法
     */
    private Method actionMethod;

    /**
     * 构造方法
     *
     * @param controllerClass Controller类
     * @param actionMethod    Action方法
     */
    public Handler(Class<?> controllerClass, Method actionMethod) {
        this.controllerClass = controllerClass;
        this.actionMethod = actionMethod;
    }

    @Override
    public String toString() {
        return "Handler{" +
                "controllerClass=" + controllerClass +
                ", actionMethod=" + actionMethod +
                '}';
    }

    // getter

    public Class<?> getControllerClass() {
        return controllerClass;
    }

    public Method getActionMethod() {
        return actionMethod;
    }
}
