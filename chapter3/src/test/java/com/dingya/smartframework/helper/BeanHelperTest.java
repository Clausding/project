package com.dingya.smartframework.helper;

import com.dingya.website.controller.CustomerController;
import org.junit.Test;

import java.util.Map;

/**
 * @Author: dingya
 * @Description:
 * @Date: Created in 9:42 2018/6/28
 */
public class BeanHelperTest {

    @Test
    public void getBean() {
        CustomerController controller = BeanHelper.getBean(CustomerController.class);
        System.out.println(controller);
    }

    @Test
    public void getBeanMap() {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        System.out.println(beanMap);
    }
}