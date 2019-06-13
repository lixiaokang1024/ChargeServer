package com.charge.service.user.impl;

import com.charge.mapper.user.UserDao;
import com.charge.pojo.User;
import com.charge.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User getUser(Integer id) {
        return userDao.getUser(id);
    }

    public User getByUserName(String username) {
        return userDao.getByUserName(username);
    }
}