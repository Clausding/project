package com.dingya.smartframework.bean;

import java.io.InputStream;

/**
 * @Author: dingya
 * @Description:封装上传文件参数
 * @Date: Created in 14:52 2018/7/10
 */
public class FileParam {
    /**
     * 文件表单的字段名
     */
    private String fieldName;
    /**
     * 上传文件名
     */
    private String fileName;
    /**
     * 上传文件大小
     */
    private long fileSize;
    /**
     * 上传文件的Content-Type，可判断文件类型
     */
    private String contentType;
    /**
     * 上传文件的字节输入流
     */
    private InputStream inputStream;

    /**
     * 构造函数
     *
     * @param fieldName   文件表单的字段名
     * @param fileName    上传文件名
     * @param fileSize    上传文件大小
     * @param contentType 上传文件的Content-Type，可判断文件类型
     * @param inputStream 上传文件的字节输入流
     */
    public FileParam(String fieldName, String fileName, long fileSize, String contentType, InputStream inputStream) {
        this.fieldName = fieldName;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.contentType = contentType;
        this.inputStream = inputStream;
    }

    // getter

    public String getFieldName() {
        return fieldName;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContentType() {
        return contentType;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
