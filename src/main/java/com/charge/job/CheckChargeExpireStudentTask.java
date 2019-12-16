package com.charge.job;

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
public class CheckChargeExpireStudentTask {

  private static final Logger logger = LoggerFactory.getLogger(CheckChargeExpireStudentTask.class);

  @Autowired
  private StudentChargeInfoService studentChargeInfoService;

  @Scheduled(cron = "0 0 0 * * ? ")
  public void checkChargeExpireStudent(){
    logger.info("checkChargeExpireStudent time = {}", DateUtil.getCurrTime());
    studentChargeInfoService.checkChargeExpireStudent();
  }
}
