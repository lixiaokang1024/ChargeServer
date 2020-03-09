package com.charge.mapper.student;

import com.charge.pojo.student.ReceiptIdRecord;

public interface ReceiptIdRecordMapper {
    int insert(ReceiptIdRecord record);
    ReceiptIdRecord select();
    void addIdRecord();
}