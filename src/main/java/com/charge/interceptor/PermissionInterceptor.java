package com.charge.interceptor;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class PermissionInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.indexOf("/login") >= 0) {
            return true;
        }
        HttpSession session = request.getSession();
        List<String> menuList = (List<String>) session.getAttribute("resource");
        if(!menuList.contains(uri.substring(8))){
            String html = "<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" /></head><body><img src='"+request.getContextPath()+"/images/403.jpg' width=100% height=100% /></body></html>";
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.getWriter().write(html);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
