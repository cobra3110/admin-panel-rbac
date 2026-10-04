package com.example.adminpanel.service;

import com.example.adminpanel.model.Permission;
import com.example.adminpanel.model.Role;
import com.example.adminpanel.repository.PermissionRepository;
import com.example.adminpanel.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository,
                       PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public List<Role> findAll() { return roleRepository.findAll(); }

    public List<Permission> findAllPermissions() { return permissionRepository.findAll(); }

    public Role findById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Роль не найдена: " + id));
    }

    public void updatePermissions(Long roleId, List<Long> permissionIds) {
        Role role = findById(roleId);
        Set<Permission> permissions = new HashSet<>(permissionRepository.findAllById(permissionIds));
        role.setPermissions(permissions);
        roleRepository.save(role);
    }
}