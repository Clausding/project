package com.dingya.website.controller;

import com.dingya.smartframework.annotation.Action;
import com.dingya.smartframework.annotation.Controller;

/**
 * @Author: dingya
 * @Description:客户信息控制器
 * @Date: Created in 16:12 2018/6/27
 */
@Controller
public class CustomerController {
    @Action("get:/customers")
    public void showCustomerList() {
    }
    @Action("get:/customer")
    public void showCustomer() {
    }
    @Action("get:/add")
    public void addCustomer() {
    }
    @Action("get:/delete")
    public void deleteCustomer() {
    }
}
