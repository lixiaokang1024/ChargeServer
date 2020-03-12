package com.charge.mapper.student;


import com.charge.pojo.student.StudentChargeIo;
import com.charge.vo.student.StudentChargeIoVo;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface StudentChargeIoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentChargeIo record);

    int insertSelective(StudentChargeIo record);

    StudentChargeIo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentChargeIo record);

    int updateByPrimaryKey(StudentChargeIo record);

    int countReceiptList(Map<String, Object> paramMap);

    List<StudentChargeIoVo> queryReceiptList(Map<String, Object> paramMap);

    void updateLeftDepositAmount(@Param("receiptId") String receiptId, @Param("leftDepositAmount") BigDecimal leftDepositAmount);
}