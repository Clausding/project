package com.dingya.smartframework.bean;

/**
 * @Author: dingya
 * @Description:返回数据对象
 * @Date: Created in 16:33 2018/6/28
 */
public class Data {
    /**
     * 数据模型
     */
    private Object model;

    /**
     * 构造方法
     *
     * @param model 数据模型
     */
    public Data(Object model) {
        this.model = model;
    }

    // getter

    public Object getModel() {
        return model;
    }
}
