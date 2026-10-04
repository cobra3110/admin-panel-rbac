package com.example.adminpanel.controller;

import com.example.adminpanel.service.RoleService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final RoleService roleService;

    public AdminController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/roles")
    public String roles(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "admin/roles";
    }

    @GetMapping("/roles/{id}/permissions")
    public String editPermissions(@PathVariable Long id, Model model) {
        model.addAttribute("role", roleService.findById(id));
        model.addAttribute("allPermissions", roleService.findAllPermissions());
        return "admin/role-permissions";
    }

    @PostMapping("/roles/{id}/permissions")
    public String updatePermissions(@PathVariable Long id,
                                    @RequestParam(required = false) List<Long> permissionIds) {
        roleService.updatePermissions(id, permissionIds == null ? List.of() : permissionIds);
        return "redirect:/admin/roles";
    }
}