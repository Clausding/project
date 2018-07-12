package com.dingya.smartframework;

import com.dingya.smartframework.bean.Data;
import com.dingya.smartframework.bean.Handler;
import com.dingya.smartframework.bean.Param;
import com.dingya.smartframework.bean.View;
import com.dingya.smartframework.helper.*;
import com.dingya.smartframework.util.JsonUtil;
import com.dingya.smartframework.util.ReflectionUtil;
import com.dingya.smartframework.util.StringUtil;

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
        ServletHelper.init(req, resp);
        try {
            String reqMethod = req.getMethod().toLowerCase();
            String reqPathInfo = req.getPathInfo();
            if (reqPathInfo.equals("/favicon.ico")) {
                return;
            }
            Handler handler = ControllerHelper.getHandler(reqMethod, reqPathInfo);
            if (handler != null) {
                // 获得 Method 对象
                Method method = handler.getActionMethod();
                // 找到 Object 对象
                Object controllerBean = BeanHelper.getBean(handler.getControllerClass());
                // 找到 Param 对象
                Param param;
                if (UploadHelper.isRequestMultipart(req)) {
                    param = UploadHelper.createParam(req);
                } else {
                    param = RequestHelper.createParam(req);
                }
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
        } finally {
            ServletHelper.destroy();
        }
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
     * 注册 Servlet 对象
     *
     * @param servletContext
     */
    public void registerServlet(ServletContext servletContext) {
        // 注册 处理jsp的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
//        jspServlet.addMapping("index.jsp");
        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");
        // 注册 处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssertPath() + "*");
    }

    /**
     * 初始化
     *
     * @param servletConfig
     */
    @Override
    public void init(ServletConfig servletConfig) {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        registerServlet(servletContext);
        UploadHelper.init(servletContext);
    }
}
