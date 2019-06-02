package com.charge.controller;


import com.charge.pojo.User;
import com.charge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("hello")
public class HelloWorldController {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/helloworld")
    public String hello(Model model) {
        logger.info("==============================================");
        User user = userService.getUser(1);
        model.addAttribute("user", user);
        return "login";
    }
}