package com.charge.mapper.charge;

import com.charge.pojo.charge.PayProjectIo;

import java.util.List;
import java.util.Map;

public interface PayProjectIoMapper {
    int insert(PayProjectIo record);

    int insertSelective(PayProjectIo record);

    int updateByPrimaryKeySelective(PayProjectIo record);

    int updateByPrimaryKey(PayProjectIo record);

    PayProjectIo getPayProjectIoById(Integer id);

    int countPayProjectIo(Map<String, Object> param);
    List<PayProjectIo> queryPayProjectIoPageList(Map<String, Object> param);
}