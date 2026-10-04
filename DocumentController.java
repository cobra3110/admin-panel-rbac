package com.example.adminpanel.controller;

import com.example.adminpanel.model.Document;
import com.example.adminpanel.service.DocumentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('DOCUMENT_VIEW')")
    public String list(Model model) {
        model.addAttribute("documents", documentService.findAll());
        return "documents/list";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('DOCUMENT_CREATE')")
    public String newForm(Model model) {
        model.addAttribute("document", new Document());
        return "documents/form";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('DOCUMENT_CREATE')")
    public String create(@ModelAttribute Document document) {
        documentService.create(document);
        return "redirect:/documents";
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("hasAuthority('DOCUMENT_EDIT')")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("document", documentService.findById(id));
        return "documents/form";
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('DOCUMENT_EDIT')")
    public String update(@PathVariable Long id, @ModelAttribute Document document) {
        documentService.update(id, document);
        return "redirect:/documents";
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("hasAuthority('DOCUMENT_DELETE')")
    public String delete(@PathVariable Long id) {
        documentService.delete(id);
        return "redirect:/documents";
    }
}