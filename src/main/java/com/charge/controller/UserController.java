package com.charge.controller;

import com.charge.pojo.User;
import com.charge.pojo.user.Resource;
import com.charge.pojo.user.Role;
import com.charge.service.user.UserService;
import com.charge.vo.user.RoleVo;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        List<Resource> resourceList = userService.getResourceByUser(userIndb.getId());
        List<String> menuList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(resourceList)){
            for(Resource resource:resourceList){
                menuList.add(resource.getMenuKey());
            }
        }
        session.setAttribute("resource", menuList);
        List<Role> roleList = userService.getRoleByUser(userIndb.getId());
        List<Integer> roleIds = new ArrayList<>();
        if(!CollectionUtils.isEmpty(roleList)){
            for(Role role:roleList){
                roleIds.add(role.getId());
            }
        }
        session.setAttribute("role", roleIds);
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

    @RequestMapping("/userRoleList")
    @ResponseBody
    public ModelMap userRoleList(Integer userId) {
        ModelMap model = new ModelMap();
        try {
            List<RoleVo> roleList = userService.getRoleList();
            model.put("rows", roleList);
            List<Role> roleByUser = userService.getRoleByUser(userId);
            List<Integer> roleIds = new ArrayList<>();
            if(!CollectionUtils.isEmpty(roleByUser)){
                for(Role role:roleByUser){
                    roleIds.add(role.getId());
                }
            }
            model.put("roleIds", roleIds);
        }catch (Exception e){
        }
        return model;
    }


    @RequestMapping("/saveRole")
    @ResponseBody
    public Map<String, Object> saveRole(Integer roleId, String name) {
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

    @RequestMapping("/saveResource")
    @ResponseBody
    public Map<String, Object> saveResource(Integer menuId, String menuKey, String menuName) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            Resource resource = new Resource();
            resource.setId(menuId);
            resource.setMenuKey(menuKey);
            resource.setMenuName(menuName);
            userService.saveOrUpdateResource(resource);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public Map<String, Object> saveUser(Integer userId, String userName, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            User user = new User();
            user.setId(userId);
            user.setUserName(userName);
            user.setPassword(password);
            userService.saveUser(user);
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/saveUserRole")
    @ResponseBody
    public Map<String, Object> saveUserRole(@RequestParam("userId")Integer userId, @RequestParam("roleIds[]") List<Integer> roleIds) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            if(!CollectionUtils.isEmpty(roleIds)){
                userService.saveUserRole(userId, roleIds);
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/saveRoleResource")
    @ResponseBody
    public Map<String, Object> saveRoleResource(@RequestParam("roleId")Integer roleId, @RequestParam("resourceIds[]") List<Integer> resourceIds) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            if(!CollectionUtils.isEmpty(resourceIds)){
                userService.saveRoleResource(roleId, resourceIds);
            }
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/menuIndex")
    public String toMenuIndex(){
        return "user/resource";
    }
    @RequestMapping("/menuList")
    @ResponseBody
    public Map<String, Object> menuList(Integer roleId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            resultMap.put("rows", userService.getResourceList());
        } catch (Exception e) {
            resultMap.put("success", false);
            resultMap.put("msg", e.getMessage());
        }
        return resultMap;
    }

    @RequestMapping("/roleMenuList")
    @ResponseBody
    public Map<String, Object> roleMenuList(Integer roleId) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", true);
        try {
            resultMap.put("rows", userService.getResourceList());
            List<Resource> resourceByRole = userService.getResourceByRole(roleId);
            List<Integer> roleResourceIds = new ArrayList<>();
            if(!CollectionUtils.isEmpty(resourceByRole)){
                for(Resource resource:resourceByRole){
                    roleResourceIds.add(resource.getId());
                }
            }
            resultMap.put("roleMenus", roleResourceIds);
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
