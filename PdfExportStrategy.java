package com.example.adminpanel.factory;

import com.example.adminpanel.model.Employee;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class PdfExportStrategy implements ExportStrategy<Employee> {

    @Override
    public byte[] export(List<Employee> employees) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PDF EXPORT (demo) ===\n");
        sb.append("Сотрудников: ").append(employees.size()).append("\n\n");
        for (Employee e : employees) {
            sb.append("- ").append(e.getFirstName()).append(" ")
                    .append(e.getLastName()).append(" | ")
                    .append(e.getPosition()).append("\n");
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getFormat() { return "PDF"; }
}