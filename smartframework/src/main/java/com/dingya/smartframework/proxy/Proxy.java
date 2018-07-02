package com.dingya.smartframework.proxy;

/**
 * @Author: dingya
 * @Description:代理接口，它的实现类实现横切逻辑
 * @Date: Created in 9:47 2018/7/2
 */
public interface Proxy {
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
