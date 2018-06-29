package com.dingya.website.service;

import com.dingya.smartframework.annotation.Inject;
import com.dingya.website.bean.Customer;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: dingya
 * @Description:
 * @Date: Created in 14:58 2018/6/29
 */
public class CustomerServiceTest {

    @Test
    public void getCustomerList() {
        List<Customer> customerList = new CustomerService().getCustomerList();
        for (Customer customer:
             customerList) {
            System.out.println(customer);
        }
    }
}