package com.example.adminpanel.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ПАТТЕРН "СТРОИТЕЛЬ" (Builder).
 * Пошаговое создание иммутабельного DTO с валидацией на этапе build().
 */
public final class EmployeeDTO {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String position;
    private final BigDecimal salary;
    private final LocalDate hireDate;

    private EmployeeDTO(Builder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.position = builder.position;
        this.salary = builder.salary;
        this.hireDate = builder.hireDate;
    }

    public static Builder builder() { return new Builder(); }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPosition() { return position; }
    public BigDecimal getSalary() { return salary; }
    public LocalDate getHireDate() { return hireDate; }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String position;
        private BigDecimal salary;
        private LocalDate hireDate;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder firstName(String v) { this.firstName = v; return this; }
        public Builder lastName(String v) { this.lastName = v; return this; }
        public Builder email(String v) { this.email = v; return this; }
        public Builder position(String v) { this.position = v; return this; }
        public Builder salary(BigDecimal v) { this.salary = v; return this; }
        public Builder hireDate(LocalDate v) { this.hireDate = v; return this; }

        public EmployeeDTO build() {
            if (firstName == null || firstName.isBlank())
                throw new IllegalStateException("Имя обязательно");
            if (lastName == null || lastName.isBlank())
                throw new IllegalStateException("Фамилия обязательна");
            if (email == null || !email.contains("@"))
                throw new IllegalStateException("Некорректный email");
            return new EmployeeDTO(this);
        }
    }
}