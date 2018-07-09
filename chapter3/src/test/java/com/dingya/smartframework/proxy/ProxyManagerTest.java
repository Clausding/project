package com.dingya.smartframework.proxy;

import com.dingya.website.aspect.ControllerAspect;
import com.dingya.website.controller.CustomerController;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dingya
 * @Description:
 * @Date: Created in 17:19 2018/7/5
 */
public class ProxyManagerTest {
    @Test
    public void test1() {
        Class targetClass = CustomerController.class;
        List<Proxy> proxyList = new ArrayList<Proxy>();
        proxyList.add(new ControllerAspect());
        CustomerController proxy = ProxyManager.createProxy(targetClass, proxyList);
    }
}
