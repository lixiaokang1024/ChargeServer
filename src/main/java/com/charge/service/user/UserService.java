package com.charge.service.user;

import com.charge.pojo.User;
import com.charge.pojo.user.Resource;
import com.charge.pojo.user.Role;
import com.charge.vo.user.RoleVo;
import java.util.List;

public interface UserService {

    User getUser(Integer id);
    void saveUser(User user);
    List<User> getUserList();

    User getByUserName(String username);

    List<RoleVo> getRoleList();
    void saveRole(String name);

    void saveUserRole(Integer userId, List<Integer> roleIds);

    void saveOrUpdateResource(Resource resource);

    List<Resource> getResourceByUser(Integer userId);
    List<Resource> getResourceByRole(Integer roleId);
    List<Resource> getResourceList();
    List<Role> getRoleByUser(Integer userId);

  void saveRoleResource(Integer roleId, List<Integer> resourceIds);
}