package com.charge.interceptor;

import com.charge.pojo.User;
import com.charge.pojo.user.Resource;
import com.charge.util.JsonUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        User user = (User) session.getAttribute("user");
        if(user.getUserName().equals("admin")){
            return true;
        }
        List<String> menuList = (List<String>) session.getAttribute("resource");
        if(!menuList.contains(uri)){
            Map<String, String> obj = new HashMap<>();
            obj.put("msg", "权限不足");
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.toJson(obj));
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
