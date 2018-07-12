package com.dingya.smartframework.helper;

import com.dingya.smartframework.bean.FileParam;
import com.dingya.smartframework.bean.FormParam;
import com.dingya.smartframework.bean.Param;
import com.dingya.smartframework.util.FileUtil;
import com.dingya.smartframework.util.StreamUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: dingya
 * @Description:
 * @Date: Created in 16:51 2018/7/10
 */
public class UploadHelper {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    /**
     * org.apache.commons.fileupload 提供的 servlet文件上传类
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * 对 servletFileUpload 对象进行初始化
     *
     * @param servletContext
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否是 Multipart 类型
     *
     * @param request
     * @return
     */
    public static boolean isRequestMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建一个 参数对象
     *
     * @param request
     * @return
     */
    public static Param createParam(HttpServletRequest request) {
        //表单参数容器
        ArrayList<FormParam> formParamList = new ArrayList<FormParam>();
        //文件参数容器
        ArrayList<FileParam> fileParamList = new ArrayList<FileParam>();
        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            if (!MapUtils.isEmpty(fileItemListMap)) {
                for (Map.Entry<String, List<FileItem>> entry : fileItemListMap.entrySet()) {
                    String fieldName = entry.getKey();
                    List<FileItem> fileItemList = entry.getValue();
                    for (FileItem fileItem : fileItemList) {
                        if (fileItem.isFormField()) {
                            String fieldValue = fileItem.getString("UTF-8");
                            // 添加值到 表单参数容器
                            formParamList.add(new FormParam(fieldName, fieldValue));
                        } else {
                            String fileName = FilenameUtils.getName(new String(fileItem.getName().getBytes(), "UTF-8"));
                            if (!fieldName.isEmpty()) {
                                long fileSize = fileItem.getSize();
                                String contentType = fileItem.getContentType();
                                InputStream inputStream = fileItem.getInputStream();
                                // 添加值到 文件参数容器
                                fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("create param failure", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     *
     * @param basePath  基础路径
     * @param fileParam 文件参数对象
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (fileParam != null) {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传文件
     *
     * @param basePath      基础路径
     * @param fileParamList 文件参数对象集合
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        try {
            if (!CollectionUtils.isEmpty(fileParamList)) {
                for (FileParam fileParam : fileParamList) {
                    uploadFile(basePath, fileParam);
                }
            }
        } catch (Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }
}