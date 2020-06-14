package com.charge.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisUtil {

  @Autowired
  private Jedis jedis;

  public void set(String key, String value){
    jedis.set(key, value);
  }

  public boolean set(String key, String value, String nxxx, String expx, Long timeout){
    return jedis.set(key, value, nxxx, expx, timeout) != null;
  }

}
