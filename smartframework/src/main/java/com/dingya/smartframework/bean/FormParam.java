package com.dingya.smartframework.bean;

/**
 * @Author: dingya
 * @Description:封装表单参数
 * @Date: Created in 15:01 2018/7/10
 */
public class FormParam {
    /**
     * 表单字段名
     */
    private String fieldName;
    /**
     * 表单字段值
     */
    private Object fieldValue;

    /**
     * 构造方法
     *
     * @param fieldName  表单字段名
     * @param fieldValue 表单字段值
     */
    public FormParam(String fieldName, Object fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    // getter

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
