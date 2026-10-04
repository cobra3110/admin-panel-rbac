package com.example.adminpanel.event;

import com.example.adminpanel.model.Employee;
import org.springframework.context.ApplicationEvent;

public class EmployeeEvent extends ApplicationEvent {

    private final Employee employee;
    private final String action;

    public EmployeeEvent(Object source, Employee employee, String action) {
        super(source);
        this.employee = employee;
        this.action = action;
    }

    public Employee getEmployee() { return employee; }
    public String getAction() { return action; }
}