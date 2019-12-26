package com.charge.mapper.user;

import com.charge.pojo.User;
import java.util.List;

public interface UserDao {

    User getUser(Integer id);

    User getByUserName(String username);

    List<User> getAllUser();
}