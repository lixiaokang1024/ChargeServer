package com.charge.service.user.impl;

import com.charge.mapper.user.RoleMapper;
import com.charge.mapper.user.UserDao;
import com.charge.pojo.User;
import com.charge.pojo.user.Resource;
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
    public void saveUser(User user) {
      if(user.getId() != null){
        userDao.updateUser(user);
      }else{
        userDao.insertSelective(user);
      }
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

    @Override
    public void saveUserRole(Integer userId, List<Integer> roleIds) {
        userDao.deleteRoleByUserId(userId);
        for(Integer roleId:roleIds){
            userDao.saveUserRole(userId, roleId);
        }
    }

  @Override
  public void saveOrUpdateResource(Resource resource) {
    if(resource.getId() != null){
      userDao.updateResource(resource);
    }else{
      userDao.insertResource(resource);
    }
  }

    @Override
    public List<Resource> getResourceByUser(Integer userId) {
        return roleMapper.getResourceByUser(userId);
    }
    @Override
    public List<Resource> getResourceByRole(Integer roleId) {
      return roleMapper.getResourceByRole(roleId);
    }

  @Override
  public List<Resource> getResourceList() {
    return roleMapper.selectAllResource();
  }

  @Override
  public List<Role> getRoleByUser(Integer userId) {
    return roleMapper.getRoleByUser(userId);
  }

  @Override
  public void saveRoleResource(Integer roleId, List<Integer> resourceIds) {
      userDao.deleteResourceByRoleId(roleId);
    for(Integer resourceId:resourceIds){
      userDao.saveRoleResource(roleId, resourceId);
    }
  }
}