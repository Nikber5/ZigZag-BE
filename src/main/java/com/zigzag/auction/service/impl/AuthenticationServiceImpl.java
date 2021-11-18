package com.zigzag.auction.service.impl;

import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;

    public AuthenticationServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public User register(User user) {
        user.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_USER)));
        userService.create(user);
        return user;
    }
}
