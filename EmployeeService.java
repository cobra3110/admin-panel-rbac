package com.example.adminpanel.service;

import com.example.adminpanel.event.EmployeeEventPublisher;
import com.example.adminpanel.model.Employee;
import com.example.adminpanel.repository.EmployeeRepository;
import com.example.adminpanel.service.validation.ValidationStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Принцип D (Dependency Inversion): зависит от абстракции ValidationStrategy, а не от конкретного класса.
 * Принцип S (Single Responsibility): только бизнес-логика сотрудников.
 */
@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final ValidationStrategy<Employee> validator;
    private final EmployeeEventPublisher eventPublisher;

    public EmployeeService(EmployeeRepository repository,
                           ValidationStrategy<Employee> validator,
                           EmployeeEventPublisher eventPublisher) {
        this.repository = repository;
        this.validator = validator;
        this.eventPublisher = eventPublisher;
    }

    public List<Employee> findAll() { return repository.findAll(); }

    public Employee findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Сотрудник не найден: " + id));
    }

    public Employee create(Employee employee) {
        validator.validate(employee);
        Employee saved = repository.save(employee);
        eventPublisher.publishCreated(saved);
        return saved;
    }

    public Employee update(Long id, Employee data) {
        validator.validate(data);
        Employee existing = findById(id);
        existing.setFirstName(data.getFirstName());
        existing.setLastName(data.getLastName());
        existing.setEmail(data.getEmail());
        existing.setPosition(data.getPosition());
        existing.setSalary(data.getSalary());
        existing.setHireDate(data.getHireDate());
        Employee saved = repository.save(existing);
        eventPublisher.publishUpdated(saved);
        return saved;
    }

    public void delete(Long id) {
        Employee e = findById(id);
        repository.delete(e);
        eventPublisher.publishDeleted(e);
    }
}