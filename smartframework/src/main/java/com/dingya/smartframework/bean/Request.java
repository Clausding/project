package com.dingya.smartframework.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @Author: dingya
 * @Description:封装请求信息
 * @Date: Created in 10:41 2018/6/28
 */
public class Request {
    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 构造方法
     *
     * @param requestMethod 请求方法
     * @param requestPath   请求路径
     */
    public Request(String requestMethod, String requestPath) {
        this.requestMethod = requestMethod;
        this.requestPath = requestPath;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestMethod='" + requestMethod + '\'' +
                ", requestPath='" + requestPath + '\'' +
                '}';
    }

    // getter

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestPath() {
        return requestPath;
    }
}
