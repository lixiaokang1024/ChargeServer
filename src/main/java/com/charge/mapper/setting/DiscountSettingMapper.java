package com.charge.mapper.setting;

import com.charge.pojo.setting.DiscountSetting;
import com.charge.vo.setting.DiscountSettingVo;
import java.util.List;

public interface DiscountSettingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DiscountSetting record);

    int insertSelective(DiscountSetting record);

    DiscountSetting selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DiscountSetting record);

    int updateByPrimaryKey(DiscountSetting record);

    List<DiscountSettingVo> selectAll();
}