package com.example.adminpanel.factory;

import com.example.adminpanel.model.Employee;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class CsvExportStrategy implements ExportStrategy<Employee> {

    @Override
    public byte[] export(List<Employee> employees) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID,Имя,Фамилия,Email,Должность,Зарплата,Дата приёма\n");
        for (Employee e : employees) {
            sb.append(e.getId()).append(",")
                    .append(safe(e.getFirstName())).append(",")
                    .append(safe(e.getLastName())).append(",")
                    .append(safe(e.getEmail())).append(",")
                    .append(safe(e.getPosition())).append(",")
                    .append(e.getSalary() == null ? "" : e.getSalary()).append(",")
                    .append(e.getHireDate() == null ? "" : e.getHireDate())
                    .append("\n");
        }
        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    private String safe(String s) { return s == null ? "" : s.replace(",", ";"); }

    @Override
    public String getFormat() { return "CSV"; }
}