package com.charge.controller;

import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextAwareDemo implements ApplicationContextAware {

  private ApplicationContext applicationContext;

  @Autowired
  private List<ApplicationContext> applicationContextSpring;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
