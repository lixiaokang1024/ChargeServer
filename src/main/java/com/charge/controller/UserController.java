package com.charge.controller;

import com.charge.pojo.User;
import com.charge.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        result.put("status", true);
        if(userIndb == null){
            result.put("status", false);
            result.put("msg", "用户不存在");
        }else if(!userIndb.getPassword().equals(user.getPassword())){
            result.put("status", false);
            result.put("msg", "密码不正确！");
        }
        session.setAttribute("user", userIndb);
        return result;
    }

    @RequestMapping("/logout")
    public String loginOut(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
