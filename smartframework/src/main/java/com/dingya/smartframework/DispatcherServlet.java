package com.dingya.smartframework;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: dingya
 * @Description:请求转发器
 * @Date: Created in 16:41 2018/6/28
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void init(ServletConfig servletConfig) {
        HelperLoader.init();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        jspServlet.addMapping(ConfigConstant.APP_JSP_PATH + "*");
        defaultServlet.addMapping(ConfigConstant.APP_ASSET_PATH + "*");
    }
}
