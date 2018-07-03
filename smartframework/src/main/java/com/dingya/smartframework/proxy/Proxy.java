package com.dingya.smartframework.proxy;

/**
 * @Author: dingya
 * @Description:代理接口
 * @Date: Created in 9:47 2018/7/2
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
