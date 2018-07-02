package com.dingya.smartframework.annotation;

import java.lang.annotation.*;

/**
 * @Author: dingya
 * @Description:切面注解
 * @Date: Created in 9:42 2018/7/2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 切面
     */
    Class<? extends Annotation> value();
}
