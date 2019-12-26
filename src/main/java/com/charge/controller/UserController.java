package com.charge.controller;

import com.charge.pojo.User;
import com.charge.pojo.user.Role;
import com.charge.service.user.UserService;
import com.charge.vo.user.RoleVo;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping("/modifyPW")
    @ResponseBody
    public Map<String,Object> modifyPW(HttpSession session, String oldPassword, String newPassword) {
        Map<String,Object> result = new HashMap<String,Object>();
        result.put("success", true);
        result.put("msg", "成功");
        return result;
    }

    @RequestMapping("/roleIndex")
    public String toRoleIndex(){
        return "user/role";
    }
    @RequestMapping("/roleList")
    @ResponseBody
    public ModelMap roleList(String name) {
        ModelMap model = new ModelMap();
        try {
            List<RoleVo> roleList = userService.getRoleList();
            model.put("rows", roleList);
            model.put("total", 1);
            model.put("page", 1);
        }catch (Exception e){
        }
        return model;
    }

    @RequestMapping("/saveRole")
    @ResponseBody
    public Map<String, Object> saveRole(String name) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            userService.saveRole(name);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/menuList")
    @ResponseBody
    public Map<String, Object> menuList(Integer roleId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            resultMap.put("rows", userService.getRoleList());
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/userIndex")
    public String toUserIndex(){
        return "user/user";
    }
    @RequestMapping("/userList")
    @ResponseBody
    public ModelMap userList(String name) {
        ModelMap model = new ModelMap();
        try {
            List<User> userList = userService.getUserList();
            model.put("rows", userList);
            model.put("total", 1);
            model.put("page", 1);
        }catch (Exception e){
        }
        return model;
    }

}
