package com.pr_manager.http.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("properties")
public class Hello {
    @RequestMapping("hello")
    public String hello(){
        return "Properties Manager is Running";
    }
}
