package com.charge.service.discount;

import com.charge.vo.setting.DiscountSettingVo;
import java.util.List;

public interface DiscountService {

    List<DiscountSettingVo> getAll();

    void save(String discount);

    void delete(Integer id);

}