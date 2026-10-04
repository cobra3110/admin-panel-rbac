package com.example.adminpanel.repository;

import com.example.adminpanel.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}