package com.dingya.website.controller;

import com.dingya.smartframework.annotation.Action;
import com.dingya.smartframework.annotation.Controller;
import com.dingya.smartframework.annotation.Inject;
import com.dingya.smartframework.bean.Data;
import com.dingya.smartframework.bean.Param;
import com.dingya.smartframework.bean.View;
import com.dingya.website.bean.Customer;
import com.dingya.website.service.CustomerService;

import java.util.List;
import java.util.Map;

/**
 * @Author: dingya
 * @Description:处理客户管理相关请求
 * @Date: Created in 16:12 2018/6/27
 */
@Controller
public class CustomerController {
    @Inject
    private CustomerService customerService;

    /**
     * 代理测试对象
     */
    public void test1(String name) {
        System.out.println(name);
    }

    /**
     * 进入 客户列表 页面
     *
     * @param param 请求参数
     * @return 页面
     */
    @Action("get:/customer")
    public View getindex(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp").addMOdel("customerList", customerList);
    }

    /**
     * 进入 编辑用户 界面
     *
     * @param param 请求参数
     * @return 页面
     */
    @Action("get:/customer_edit")
    public View getCustomerEdit(Param param) {
        Long id = Long.parseLong((String) param.getParamMap().get("id"));
        Customer customer = customerService.getCustomer(id);
        return new View("customer_edit.jsp").addMOdel("customer", customer);
    }

    /**
     * 提交 编辑用户 表单
     *
     * @param param 请求参数
     * @return 页面
     */
    @Action("put:/customer_edit")
    public Data putCustomerEdit(Param param) {
        Long id = Long.parseLong((String) param.getParamMap().get("id"));
        Map<String, Object> paramMap = param.getParamMap();
        boolean isSuccess = customerService.updateCustomer(id, paramMap);
        return new Data(isSuccess);
    }
}
