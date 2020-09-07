package com.charge.job;

import com.charge.mapper.student.ReceiptIdRecordMapper;
import com.charge.service.student.StudentChargeInfoService;
import com.charge.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class DeleteReceiptIdTask {

  private static final Logger logger = LoggerFactory.getLogger(DeleteReceiptIdTask.class);

  @Autowired
  private ReceiptIdRecordMapper receiptIdRecordMapper;

  @Scheduled(cron = "0 0 0 * * ? ")
  public void deleteReceiptId() {
    logger.info("deleteReceiptId time = {}", DateUtil.getCurrTime());
    receiptIdRecordMapper.deleteReceiptIdRecord();
  }
}
