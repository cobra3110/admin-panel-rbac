package com.example.adminpanel.service.validation;

import com.example.adminpanel.model.Document;
import org.springframework.stereotype.Component;

@Component
public class DocumentValidator implements ValidationStrategy<Document> {

    @Override
    public void validate(Document document) {
        if (document.getTitle() == null || document.getTitle().isBlank()) {
            throw new IllegalArgumentException("Название документа обязательно");
        }
        if (document.getFileUrl() == null || document.getFileUrl().isBlank()) {
            throw new IllegalArgumentException("Ссылка на файл обязательна");
        }
    }
}