package com.dingya.smartframework.bean;

import com.dingya.smartframework.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: dingya
 * @Description:封装请求参数
 * @Date: Created in 15:35 2018/6/28
 */
public class Param {

    /**
     * 表单参数容器
     */
    private List<FormParam> formParamList;

    /**
     * 文件参数容器
     */
    private List<FileParam> fileParamList;

    /**
     * 构造方法
     *
     * @param formParamList 表单参数容器
     */
    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    /**
     * 构造方法
     *
     * @param formParamList 表单参数容器
     * @param fileParamList 文件参数容器
     */
    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获得唯一上传文件
     *
     * @param fieldName 文件表单字段名
     * @return 唯一上传文件
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> list = getFileList(fieldName);
        if (!CollectionUtils.isEmpty(list) && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获得所有上传文件
     *
     * @param fieldName 文件表单字段名
     * @return 所有上传文件
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获得上传文件映射
     *
     * @return 上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> result = new HashMap<String, List<FileParam>>();
        if (!CollectionUtils.isEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> list;
                if (result.containsKey(fieldName)) {
                    list = result.get(fieldName);
                } else {
                    list = new ArrayList<FileParam>();
                }
                list.add(fileParam);
                result.put(fieldName, list);
            }
        }
        return result;
    }

    /**
     * 获得表单参数映射
     *
     * @return 表单参数映射
     */
    public Map<String, Object> getFormParamMap() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (!CollectionUtils.isEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (result.containsKey(fieldName)) {
                    fieldValue = result.get(fieldName) + StringUtil.SEPERATOR + fieldValue;
                }
                result.put(fieldName, fieldValue);
            }
        }
        return result;
    }

    /**
     * 判断参数对象是否为空
     *
     * @return 结果布尔值
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(fileParamList) && CollectionUtils.isEmpty(formParamList);
    }

    /**
     * 获得 字符串 类型参数
     * @param key
     * @return
     */
    public String getString(String key) {
        return (String) getFormParamMap().get(key);
    }
}
