package com.zigzag.auction.service;

import com.zigzag.auction.model.Role;

public interface RoleService {
    void add(Role role);
  
    Role getRoleByName(Role.RoleName roleName);
}