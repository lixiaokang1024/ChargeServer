package com.charge.mapper.user;

import com.charge.pojo.User;
import com.charge.pojo.user.Resource;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDao {

    User getUser(Integer id);

    User getByUserName(String username);

    List<User> getAllUser();
    void insertSelective(User user);
    void updateUser(User user);

    void saveUserRole(@Param("userId") Integer userId, @Param("roleId") Integer roleId);
    void deleteRoleByUserId(Integer userId);

    void saveRoleResource(@Param("roleId") Integer roleId, @Param("resourceId") Integer resourceId);
    void deleteResourceByRoleId(Integer roleId);

    void updateResource(Resource resource);
    void insertResource(Resource resource);
}