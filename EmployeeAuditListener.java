package com.example.adminpanel.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeeAuditListener {

    private static final Logger log = LoggerFactory.getLogger(EmployeeAuditListener.class);

    @EventListener
    public void onEmployeeEvent(EmployeeEvent event) {
        log.info("[AUDIT] {}: {} {}", event.getAction(),
                event.getEmployee().getFirstName(),
                event.getEmployee().getLastName());
    }
}