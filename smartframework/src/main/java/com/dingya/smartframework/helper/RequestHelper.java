package com.dingya.smartframework.helper;

import com.dingya.smartframework.bean.FormParam;
import com.dingya.smartframework.bean.Param;
import com.dingya.smartframework.util.CodecUtil;
import com.dingya.smartframework.util.StreamUtil;
import com.dingya.smartframework.util.StringUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: dingya
 * @Description:请求对象助手类
 * @Date: Created in 14:58 2018/7/11
 */
public class RequestHelper {
    /**
     * 获取参数对象
     *
     * @param request
     * @return 参数对象
     * @throws IOException
     */
    public static Param createParam(HttpServletRequest request) throws IOException {
        List<FormParam> formParams = new ArrayList<FormParam>();
        formParams.addAll(parseParameterNames(request));
        formParams.addAll(parseInputStream(request));
        return new Param(formParams);
    }

    /**
     * 从流中获取参数
     *
     * @param request
     * @return
     * @throws IOException
     */
    private static List<FormParam> parseInputStream(HttpServletRequest request) throws IOException {
        List<FormParam> result = new ArrayList<FormParam>();
        String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
        if (!StringUtil.isEmpty(body)) {
            String[] kvs = StringUtils.splitByWholeSeparator(body, "&");
            if (kvs != null && !ArrayUtils.isEmpty(kvs)) {
                for (int i = 0; i < kvs.length; i++) {
                    String[] strings = kvs[i].split("=");
                    if (strings != null && strings.length == 2) {
                        String paramName = strings[0];
                        String paramValue = strings[1];
                        result.add(new FormParam(paramName, paramValue));
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取参数
     *
     * @param request
     * @return
     */
    private static List<FormParam> parseParameterNames(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<FormParam>();
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String fieldName = paramNames.nextElement();
            String[] fieldValues = request.getParameterValues(fieldName);
            if (!ArrayUtils.isEmpty(fieldValues)) {
                Object fieldValue;
                if (fieldValues.length == 1) {
                    fieldValue = fieldValues[0];
                } else {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < fieldValues.length; i++) {
                        sb.append(fieldValues[i]);
                        if (i != fieldValues.length - 1) {
                            sb.append(StringUtil.SEPERATOR);
                        }
                    }
                    fieldValue = sb.toString();
                }
                formParamList.add(new FormParam(fieldName, fieldValue));
            }
        }
        return formParamList;
    }


}
