package com.charge.service;

import com.charge.pojo.User;

public interface UserService {

    User getUser(Integer id);

    User getByUserName(String username);
}