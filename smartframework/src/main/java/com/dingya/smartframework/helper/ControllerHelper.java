package com.dingya.smartframework.helper;

import com.dingya.smartframework.annotation.Action;
import com.dingya.smartframework.bean.Handler;
import com.dingya.smartframework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: dingya
 * @Description:控制器助手类
 * @Date: Created in 11:18 2018/6/28
 */
public final class ControllerHelper {
    /**
     * 容器，用于存放请求和处理器的映射关系
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    // 将'@Controller'修饰的类中，'@Action'注释包含的请求信息和调用的方法存入容器

    static {
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (!CollectionUtils.isEmpty(controllerClassSet)) {
            // 遍历'@Controller'修饰的类
            for (Class<?> controllerClass : controllerClassSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                if (!ArrayUtils.isEmpty(methods)) {
                    // 遍历类中的方法
                    for (int i = 0; i < methods.length; i++) {
                        Method actionMethod = methods[i];
                        // 存单个方法的信息
                        if (actionMethod.isAnnotationPresent(Action.class)) {
                            String mapping = actionMethod.getAnnotation(Action.class).value();
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] strings = mapping.split(":");
                                if (!ArrayUtils.isEmpty(strings) && strings.length == 2) {
                                    String requestMethod = strings[0];
                                    String requestPath = strings[1];
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, actionMethod);
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取handler
     *
     * @param requestMethod 请求方法
     * @param requestPath   请求路径
     * @return 处理器
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        Handler handler = ACTION_MAP.get(request);
        return handler;
    }
}