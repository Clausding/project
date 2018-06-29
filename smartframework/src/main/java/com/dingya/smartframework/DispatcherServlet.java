package com.dingya.smartframework;

import com.dingya.smartframework.bean.Data;
import com.dingya.smartframework.bean.Handler;
import com.dingya.smartframework.bean.Param;
import com.dingya.smartframework.bean.View;
import com.dingya.smartframework.helper.BeanHelper;
import com.dingya.smartframework.helper.ConfigHelper;
import com.dingya.smartframework.helper.ControllerHelper;
import com.dingya.smartframework.util.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: dingya
 * @Description:请求转发器,作用：处理所有的请求
 * @Date: Created in 16:41 2018/6/28
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取Handler对象
        String reqMethod = req.getMethod().toLowerCase();
        String reqPathInfo = req.getPathInfo();
        Handler handler = ControllerHelper.getHandler(reqMethod, reqPathInfo);
        Class<?> controllerClass = handler.getControllerClass();
        Object controllerBean = BeanHelper.getBean(controllerClass);

        // 获取Param对象
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 取出参数
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String paramValue = req.getParameter(paramName);
            paramMap.put(paramName, paramValue);
        }
        // 取出请求体中的参数
        String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
        if (!StringUtil.isEmpty(body)) {
            String[] params = StringUtils.splitByWholeSeparator(body, "&");
            if (params != null && !ArrayUtils.isEmpty(params)) {
                for (int i = 0; i < params.length; i++) {
                    String[] strings = params[i].split("=");
                    if (strings != null && strings.length == 2) {
                        String paramName = strings[0];
                        String paramValue = strings[1];
                        paramMap.put(paramName, paramValue);
                    }
                }
            }
        }
        Param param = new Param(paramMap);

        // 调用控制器方法
        Method method = handler.getActionMethod();
        Object result = ReflectionUtil.invokeMethod(controllerBean, method, param);

        // 根据控制器方法返回值，返回JSP页面或JSON数据
        if (result instanceof View) {
            View view = (View) result;
            String viewPath = view.getPath();
            if (!StringUtil.isEmpty(viewPath)) {
                if (viewPath.startsWith("/")) {
                    resp.sendRedirect(req.getContextPath() + viewPath);
                } else {
                    Map<String, Object> model = view.getModel();
                    for (Map.Entry<String, Object> entry:
                         model.entrySet()) {
                        req.setAttribute(entry.getKey(), entry.getValue());
                    }
                    req.getRequestDispatcher(ConfigHelper.getAppJspPath() + viewPath).forward(req,resp);
                }
            }
        } else if (result instanceof Data) {
            Data data = (Data) result;
            Object model = data.getModel();
            if (model != null) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                PrintWriter writer = resp.getWriter();
                String json = JsonUtil.toJson(model);
                writer.write(json);
                writer.flush();
                writer.close();
            }
        }
    }

    @Override
    public void init(ServletConfig servletConfig) {
        // bean容器，依赖注入和请求和处理器关系 初始化
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        // 注册处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        // 注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");
    }
}
