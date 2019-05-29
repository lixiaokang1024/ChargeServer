package com.charge.service.impl;

import com.charge.mapper.UserDao;
import com.charge.pojo.User;
import com.charge.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public User getUser(Integer id) {
        return userDao.getUser(id);
    }
}