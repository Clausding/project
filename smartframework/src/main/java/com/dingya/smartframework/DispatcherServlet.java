package com.dingya.smartframework;

import com.dingya.smartframework.bean.*;
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
        // 找到 Param 对象
        Param param = getParam(req);
        // 找到 Method 对象
        Handler handler = getHandler(req);
        Method method = handler.getActionMethod();
        // 找到 Object 对象
        Object controllerBean = BeanHelper.getBean(handler.getControllerClass());

        // 调用控制器方法
        Object result = null;
        if (param.isEmpty()) {
            result = ReflectionUtil.invokeMethod(controllerBean, method);
        } else {
            result = ReflectionUtil.invokeMethod(controllerBean, method, param);
        }

        // 根据控制器方法返回值，返回JSP页面或JSON数据
        if (result instanceof View) {
            handleViewResult((View) result, req, resp);
        } else if (result instanceof Data) {
            handleDataResult((Data) result, req, resp);
        }
    }

    /**
     * 获得 Param 对象
     *
     * @param req  Request 对象
     * @return 请求参数对象
     */
    private Param getParam(HttpServletRequest req) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        // 取出请求链接中的参数
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
        return new Param(paramMap);
    }

    /**
     * 获得 Handler 对象
     *
     * @param req  Request 对象
     * @return 封装了Action信息的对象
     */
    private Handler getHandler(HttpServletRequest req) {
        String reqMethod = req.getMethod().toLowerCase();
        String reqPathInfo = req.getPathInfo();
        return ControllerHelper.getHandler(reqMethod, reqPathInfo);
    }

    /**
     * 处理数据结果
     *
     * @param data
     * @param req
     * @param resp
     * @throws IOException
     */
    private void handleDataResult(Data data, HttpServletRequest req, HttpServletResponse resp) throws IOException {
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

    /**
     * 处理视图结果
     *
     * @param view
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void handleViewResult(View view, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String viewPath = view.getPath();
        if (!StringUtil.isEmpty(viewPath)) {
            if (viewPath.startsWith("/")) {
                resp.sendRedirect(req.getContextPath() + viewPath);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry :
                        model.entrySet()) {
                    req.setAttribute(entry.getKey(), entry.getValue());
                }
                req.getRequestDispatcher(ConfigHelper.getAppJspPath() + viewPath).forward(req, resp);
            }
        }
    }

    /**
     * 初始化
     *
     * @param servletConfig
     */
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
