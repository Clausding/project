package com.dingya.website.controller;

import com.dingya.smartframework.annotation.Action;
import com.dingya.smartframework.annotation.Controller;
import com.dingya.smartframework.annotation.Inject;
import com.dingya.smartframework.bean.Data;
import com.dingya.smartframework.bean.FileParam;
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
     * 进入 客户列表 页面
     *
     * @return 页面
     */
    @Action("get:/customer")
    public View getindex() {
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
        Long id = Long.parseLong(param.getString("id"));
        Customer customer = customerService.getCustomer(id);
        return new View("customer_edit.jsp").addMOdel("customer", customer);
    }

    /**
     * 点击 删除用户 按钮
     *
     * @param param 请求参数
     * @return 数据
     */
    @Action("get:/customer_delete")
    public Data deleteCustomer(Param param) {
        Long id = Long.parseLong(param.getString("id"));
        boolean isSuccess = customerService.deleteCustomer(id);
        return new Data(isSuccess);
    }

    /**
     * 提交 编辑用户 表单
     *
     * @param param 请求参数
     * @return 页面
     */
    @Action("put:/customer_edit")
    public Data putCustomerEdit(Param param) {
        Long id = Long.parseLong(param.getString("id"));
        Map<String, Object> paramMap = param.getFormParamMap();
        boolean isSuccess = customerService.updateCustomer(id, paramMap);
        return new Data(isSuccess);
    }

    /**
     * 进入 创建用户 界面
     *
     * @return 页面
     */
    @Action("get:/customer_create")
    public View getCustomerCreate() {
        return new View("customer_create.jsp");
    }

    /**
     * 创建用户页面：点击 创建用户 按钮
     *
     * @param param 请求参数
     * @return 数据
     */
    @Action("post:/customer_create")
    public Data createCustomer(Param param) {
        Map<String, Object> paramMap = param.getFormParamMap();
        FileParam fileParam = param.getFile("photo");
        boolean isSuccess = customerService.createCustomer(paramMap, fileParam);
        return new Data(isSuccess);
    }
}
