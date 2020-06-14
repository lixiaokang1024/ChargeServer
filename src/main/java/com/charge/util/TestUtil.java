package com.charge.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
@RunWith(value = SpringJUnit4ClassRunner.class)
public class TestUtil {

  @Autowired
  private RedisUtil redisUtil;

  @Test
  public void redisTest(){
    System.out.println(redisUtil.set("002", "001", "NX", "EX", 30L));
  }
}
