package com.dingya.smartframework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dingya
 * @Description:返回视图对象
 * @Date: Created in 16:23 2018/6/28
 */
public class View {
    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String, Object> model;

    /**
     * 构造方法
     *
     * @param path
     */
    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    /**
     * 向模型数据中添加数据
     * @param key   键
     * @param value 值
     * @return 视图对象
     */
    public View addMOdel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    // getter

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }
}
