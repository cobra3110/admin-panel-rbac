package com.example.adminpanel.controller;

import com.example.adminpanel.dto.EmployeeDTO;
import com.example.adminpanel.factory.ExportFactory;
import com.example.adminpanel.factory.ExportStrategy;
import com.example.adminpanel.model.Employee;
import com.example.adminpanel.service.EmployeeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ExportFactory exportFactory;

    public EmployeeController(EmployeeService employeeService, ExportFactory exportFactory) {
        this.employeeService = employeeService;
        this.exportFactory = exportFactory;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    public String list(Model model) {
        List<EmployeeDTO> dtos = employeeService.findAll().stream()
                .map(e -> EmployeeDTO.builder()
                        .id(e.getId())
                        .firstName(e.getFirstName())
                        .lastName(e.getLastName())
                        .email(e.getEmail())
                        .position(e.getPosition())
                        .salary(e.getSalary())
                        .hireDate(e.getHireDate())
                        .build())
                .toList();
        model.addAttribute("employees", dtos);
        return "employees/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE')")
    public String newForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "employees/form";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EMPLOYEE_CREATE')")
    public String create(@ModelAttribute Employee employee) {
        employeeService.create(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('EMPLOYEE_EDIT')")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('EMPLOYEE_EDIT')")
    public String update(@PathVariable Long id, @ModelAttribute Employee employee) {
        employeeService.update(id, employee);
        return "redirect:/employees";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('EMPLOYEE_DELETE')")
    public String delete(@PathVariable Long id) {
        employeeService.delete(id);
        return "redirect:/employees";
    }

    @GetMapping("/export")
    @PreAuthorize("hasAuthority('EMPLOYEE_VIEW')")
    public ResponseEntity<byte[]> export(@RequestParam(defaultValue = "CSV") String format) {
        ExportStrategy<Employee> strategy = exportFactory.getEmployeeStrategy(format);
        byte[] data = strategy.export(employeeService.findAll());
        String filename = "employees." + format.toLowerCase();
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }
}