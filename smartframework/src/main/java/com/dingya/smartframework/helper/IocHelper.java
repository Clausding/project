package com.dingya.smartframework.helper;

import com.dingya.smartframework.annotation.Inject;
import com.dingya.smartframework.util.ReflectionUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @Author: dingya
 * @Description:依赖注入助手类
 * @Date: Created in 9:51 2018/6/28
 */
public class IocHelper {
    static {
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        if (!MapUtils.isEmpty(beanMap)) {
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                // 获得bean类和bean
                Class<?> beanClass = beanEntry.getKey();
                Object bean = beanEntry.getValue();
                Field[] fields = beanClass.getDeclaredFields();
                if (!ArrayUtils.isEmpty(fields)) {
                    //遍历这个类的所有成员变量
                    for (Field beanField : fields) {
                        if (beanField.isAnnotationPresent(Inject.class)) {
                            Class<?> beanFieldClass = beanField.getType();
                            Object beanFieldInstance = BeanHelper.getBean(beanFieldClass);
                            if (beanFieldInstance != null) {
                                ReflectionUtil.setField(bean, beanField, beanFieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}
