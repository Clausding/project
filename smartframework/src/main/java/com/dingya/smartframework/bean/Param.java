package com.dingya.smartframework.bean;

import java.util.Map;

/**
 * @Author: dingya
 * @Description:封装请求参数
 * @Date: Created in 15:35 2018/6/28
 */
public class Param {

    /**
     * 参数容器
     */
    private Map<String, Object> paramMap;

    /**
     * 构造方法
     *
     * @param paramMap 参数容器
     */
    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 返回参数容器
     *
     * @return 参数容器
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
