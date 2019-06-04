package com.charge.controller;

import com.charge.pojo.User;
import com.charge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(User user, HttpSession session) {
        logger.info("username = {}", user.getUserName());
        User userIndb = userService.getByUserName(user.getUserName());
        Map<String,Object> result = new HashMap<String,Object>();
        if(userIndb == null){
            result.put("status", false);
            result.put("msg", "用户不存在");
        }
        session.setAttribute("user", userIndb);
        result.put("status", true);
        return result;
    }

    @RequestMapping("/logout")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
