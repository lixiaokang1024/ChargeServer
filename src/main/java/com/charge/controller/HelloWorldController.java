package com.charge.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("hello")
public class HelloWorldController {


    @RequestMapping("/hellworld")
    public String hello() {
        return "hello";
    }
}