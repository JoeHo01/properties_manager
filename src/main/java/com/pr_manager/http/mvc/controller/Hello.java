package com.pr_manager.http.mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class Hello {
    @RequestMapping
    public String hello(){
        return "Hello World";
    }
}
