package com.example.adminpanel.factory;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * ПАТТЕРН "ФАБРИКА" (Factory).
 * Создаёт нужную стратегию экспорта по её формату.
 * Spring автоматически инжектит все реализации ExportStrategy в список.
 */
@Component
public class ExportFactory {

    private final Map<String, ExportStrategy<?>> strategies;

    public ExportFactory(List<ExportStrategy<?>> strategyList) {
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        s -> s.getFormat().toUpperCase(),
                        Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public ExportStrategy<com.example.adminpanel.model.Employee> getEmployeeStrategy(String format) {
        ExportStrategy<?> strategy = strategies.get(format.toUpperCase());
        if (strategy == null) {
            throw new IllegalArgumentException("Неподдерживаемый формат: " + format);
        }
        return (ExportStrategy<com.example.adminpanel.model.Employee>) strategy;
    }
}