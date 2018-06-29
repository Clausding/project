package com.dingya.website.service;

import com.dingya.smartframework.annotation.Service;
import com.dingya.smartframework.helper.DatabaseHelper;
import com.dingya.website.bean.Customer;

import java.util.List;
import java.util.Map;

/**
 * @Author: dingya
 * @Description:客户信息服务类
 * @Date: Created in 16:13 2018/6/27
 */
@Service
public class CustomerService {

    /**
     * 获得用户列表
     *
     * @return 用户列表
     */
    public List<Customer> getCustomerList() {
        String sql = " SELECT * FROM customer ";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    /**
     * 根据用户id 查询用户
     *
     * @param id 用户id
     * @return 用户对象
     */
    public Customer getCustomer(Long id) {
        String sql = " SELECT * FROM customer WHERE id = ?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * 更新用户
     *
     * @param id       用户id
     * @param paramMap 请求参数集合
     * @return 布尔值
     */
    public boolean updateCustomer(Long id, Map<String, Object> paramMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, paramMap);
    }
}
