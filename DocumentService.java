package com.example.adminpanel.service;

import com.example.adminpanel.model.Document;
import com.example.adminpanel.repository.DocumentRepository;
import com.example.adminpanel.service.validation.ValidationStrategy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository repository;
    private final ValidationStrategy<Document> validator;

    public DocumentService(DocumentRepository repository,
                           ValidationStrategy<Document> validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<Document> findAll() { return repository.findAll(); }

    public Document findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Документ не найден: " + id));
    }

    public Document create(Document doc) {
        validator.validate(doc);
        if (doc.getCreatedDate() == null) doc.setCreatedDate(LocalDate.now());
        return repository.save(doc);
    }

    public Document update(Long id, Document data) {
        validator.validate(data);
        Document existing = findById(id);
        existing.setTitle(data.getTitle());
        existing.setCategory(data.getCategory());
        existing.setFileUrl(data.getFileUrl());
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }
}