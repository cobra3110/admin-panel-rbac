package com.example.adminpanel.event;

import com.example.adminpanel.model.Employee;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * ПАТТЕРН "НАБЛЮДАТЕЛЬ" (Observer) — издатель (Subject).
 * Публикует события об изменениях сотрудников.
 */
@Component
public class EmployeeEventPublisher {

    private final ApplicationEventPublisher publisher;

    public EmployeeEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishCreated(Employee e) { publisher.publishEvent(new EmployeeEvent(this, e, "CREATED")); }
    public void publishUpdated(Employee e) { publisher.publishEvent(new EmployeeEvent(this, e, "UPDATED")); }
    public void publishDeleted(Employee e) { publisher.publishEvent(new EmployeeEvent(this, e, "DELETED")); }
}