package com.example.adminpanel.config;

import com.example.adminpanel.model.*;
import com.example.adminpanel.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepo;
    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final EmployeeRepository employeeRepo;
    private final DocumentRepository documentRepo;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PermissionRepository permissionRepo,
                           RoleRepository roleRepo,
                           UserRepository userRepo,
                           EmployeeRepository employeeRepo,
                           DocumentRepository documentRepo,
                           PasswordEncoder passwordEncoder) {
        this.permissionRepo = permissionRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.employeeRepo = employeeRepo;
        this.documentRepo = documentRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Permissions
        Permission viewEmp = permissionRepo.save(new Permission("EMPLOYEE_VIEW", "Просмотр сотрудников"));
        Permission createEmp = permissionRepo.save(new Permission("EMPLOYEE_CREATE", "Создание сотрудников"));
        Permission editEmp = permissionRepo.save(new Permission("EMPLOYEE_EDIT", "Редактирование сотрудников"));
        Permission deleteEmp = permissionRepo.save(new Permission("EMPLOYEE_DELETE", "Удаление сотрудников"));
        Permission viewDoc = permissionRepo.save(new Permission("DOCUMENT_VIEW", "Просмотр документов"));
        Permission createDoc = permissionRepo.save(new Permission("DOCUMENT_CREATE", "Создание документов"));
        Permission editDoc = permissionRepo.save(new Permission("DOCUMENT_EDIT", "Редактирование документов"));
        Permission deleteDoc = permissionRepo.save(new Permission("DOCUMENT_DELETE", "Удаление документов"));
        Permission manageRoles = permissionRepo.save(new Permission("ROLE_MANAGE", "Управление ролями"));

        // Roles
        Role admin = new Role("ADMIN");
        admin.setPermissions(Set.of(viewEmp, createEmp, editEmp, deleteEmp,
                viewDoc, createDoc, editDoc, deleteDoc, manageRoles));
        roleRepo.save(admin);

        Role hr = new Role("HR_MANAGER");
        hr.setPermissions(Set.of(viewEmp, createEmp, editEmp, viewDoc, createDoc, editDoc));
        roleRepo.save(hr);

        Role clerk = new Role("CLERK");
        clerk.setPermissions(Set.of(viewEmp, viewDoc));
        roleRepo.save(clerk);

        // Users
        User adminUser = new User("admin", passwordEncoder.encode("admin123"));
        adminUser.setRoles(Set.of(admin));
        userRepo.save(adminUser);

        User hrUser = new User("hr_manager", passwordEncoder.encode("hr123"));
        hrUser.setRoles(Set.of(hr));
        userRepo.save(hrUser);

        User clerkUser = new User("clerk", passwordEncoder.encode("clerk123"));
        clerkUser.setRoles(Set.of(clerk));
        userRepo.save(clerkUser);

        // Demo data
        Employee e1 = new Employee();
        e1.setFirstName("Иван"); e1.setLastName("Петров");
        e1.setEmail("ivan@example.com"); e1.setPosition("Разработчик");
        e1.setSalary(new BigDecimal("150000")); e1.setHireDate(LocalDate.of(2023, 5, 10));
        employeeRepo.save(e1);

        Employee e2 = new Employee();
        e2.setFirstName("Мария"); e2.setLastName("Сидорова");
        e2.setEmail("maria@example.com"); e2.setPosition("HR-менеджер");
        e2.setSalary(new BigDecimal("120000")); e2.setHireDate(LocalDate.of(2022, 9, 1));
        employeeRepo.save(e2);

        Document d1 = new Document();
        d1.setTitle("Трудовой договор Иванова"); d1.setCategory("Договор");
        d1.setFileUrl("/files/contract_ivanov.pdf"); d1.setCreatedDate(LocalDate.now());
        documentRepo.save(d1);
    }
}