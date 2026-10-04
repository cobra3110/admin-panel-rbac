package com.example.adminpanel.service.validation;

import com.example.adminpanel.model.Employee;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class EmployeeValidator implements ValidationStrategy<Employee> {

    @Override
    public void validate(Employee employee) {
        if (employee.getFirstName() == null || employee.getFirstName().isBlank()) {
            throw new IllegalArgumentException("Имя сотрудника обязательно");
        }
        if (employee.getLastName() == null || employee.getLastName().isBlank()) {
            throw new IllegalArgumentException("Фамилия сотрудника обязательна");
        }
        if (employee.getEmail() == null || !employee.getEmail().contains("@")) {
            throw new IllegalArgumentException("Некорректный email");
        }
        if (employee.getSalary() != null && employee.getSalary().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Зарплата не может быть отрицательной");
        }
    }
}