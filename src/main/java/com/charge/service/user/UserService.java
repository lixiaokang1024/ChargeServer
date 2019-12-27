package com.charge.service.user;

import com.charge.pojo.User;
import com.charge.pojo.user.Resource;
import com.charge.vo.user.RoleVo;
import java.util.List;

public interface UserService {

    User getUser(Integer id);
    void saveUser(String username, String password);
    List<User> getUserList();

    User getByUserName(String username);

    List<RoleVo> getRoleList();
    void saveRole(String name);

    List<Resource> getResourceByUser(Integer userId);

}