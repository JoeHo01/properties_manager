package com.pr_manager.http.listener;

import com.pr_manager.http.mvc.service.ExplanationService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class StartUpListener implements ServletContextListener {
    @Autowired
    ExplanationService explanationService;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        explanationService.getDoc();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
