package com.charge.mapper;

import com.charge.pojo.User;

public interface UserDao {

    User getUser(Integer id);

    User getByUserName(String username);
}