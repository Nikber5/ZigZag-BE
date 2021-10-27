package com.zigzag.auction.service;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Role;
import com.zigzag.auction.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void add(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleByName(Role.RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new DataProcessingException("Role with name " + roleName + " not found"));
    }
}
