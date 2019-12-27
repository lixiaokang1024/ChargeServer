package com.charge.service.user.impl;

import com.charge.mapper.user.RoleMapper;
import com.charge.mapper.user.UserDao;
import com.charge.pojo.User;
import com.charge.pojo.user.Role;
import com.charge.service.user.UserService;
import com.charge.util.DateUtil;
import com.charge.vo.user.RoleVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleMapper roleMapper;

    public User getUser(Integer id) {
        return userDao.getUser(id);
    }

    @Override
    public void saveUser(String username, String password) {
        User user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setAge(10);
        userDao.insertSelective(user);
    }

    @Override
    public List<User> getUserList() {
        return userDao.getAllUser();
    }

    public User getByUserName(String username) {
        return userDao.getByUserName(username);
    }

    @Override
    public List<RoleVo> getRoleList() {
        return roleMapper.selectAll();
    }

    @Override
    public void saveRole(String name) {
        Role role = new Role();
        role.setCreateTime(DateUtil.getCurrentTimespan());
        role.setName(name);
        roleMapper.insertSelective(role);
    }
}