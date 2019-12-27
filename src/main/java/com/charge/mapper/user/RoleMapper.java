package com.charge.mapper.user;


import com.charge.pojo.user.Resource;
import com.charge.pojo.user.Role;
import com.charge.vo.user.RoleVo;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<RoleVo> selectAll();
    List<Resource> getResourceByUser(Integer userId);
}