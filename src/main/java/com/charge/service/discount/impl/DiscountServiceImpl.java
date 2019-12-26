package com.charge.service.discount.impl;

import com.charge.mapper.setting.DiscountSettingMapper;
import com.charge.pojo.setting.DiscountSetting;
import com.charge.service.discount.DiscountService;
import com.charge.util.DateUtil;
import com.charge.vo.setting.DiscountSettingVo;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountServiceImpl implements DiscountService {

  @Autowired
  private DiscountSettingMapper discountSettingMapper;

  @Override
  public List<DiscountSettingVo> getAll() {
    return discountSettingMapper.selectAll();
  }

  @Override
  public void save(String discount) {
    DiscountSetting discountSetting = new DiscountSetting();
    discountSetting.setCreateTime(DateUtil.getCurrentTimespan());
    discountSetting.setDiscount(BigDecimal.valueOf(Double.valueOf(discount)));
    discountSettingMapper.insertSelective(discountSetting);
  }

  @Override
  public void delete(Integer id) {
    discountSettingMapper.deleteByPrimaryKey(id);
  }
}
