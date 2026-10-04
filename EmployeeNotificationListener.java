package com.example.adminpanel.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmployeeNotificationListener {

    private static final Logger log = LoggerFactory.getLogger(EmployeeNotificationListener.class);

    @Async
    @EventListener
    public void onEmployeeEvent(EmployeeEvent event) {
        if ("CREATED".equals(event.getAction())) {
            log.info("[EMAIL] Новый сотрудник: {}", event.getEmployee().getEmail());
        }
    }
}