package com.pr_manager.http.mvc.controller;

import com.pr_manager.http.mvc.service.ExplanationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("properties/explanation")
public class PropertiesController {

    private final ExplanationService explanationService;

    @Autowired
    public PropertiesController(ExplanationService explanationService) {
        this.explanationService = explanationService;
    }

    @RequestMapping("getDoc")
    public String getExplanation(){
        return explanationService.getDoc();
    }
}
