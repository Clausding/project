package com.dingya.smartframework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @Author: dingya
 * @Description:JSON 工具类
 * @Date: Created in 10:26 2018/6/29
 */
public class JsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将JSON转为POJO
     *
     * @param json JSON字符串
     * @param type POJO的类型
     * @param <T>
     * @return POJO对象
     */
    public static <T> T toPojo(String json, Class<T> type) {
        T pojo;
        try {
            pojo = OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            LOGGER.error("convert JSON to POJO failure", e);
            throw new RuntimeException(e);
        }
        return pojo;
    }

    /**
     * 将POJO转为JSON
     *
     * @param pojo POJO对象
     * @param <T>
     * @return JSON字符串
     */
    public static <T> String toJson(T pojo) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(pojo);
        } catch (JsonProcessingException e) {
            LOGGER.error("convert POJO to JSON failure", e);
            throw new RuntimeException(e);
        }
        return json;
    }
}
