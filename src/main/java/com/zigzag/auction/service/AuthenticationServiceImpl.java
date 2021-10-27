package com.zigzag.auction.service;

import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;

    public AuthenticationServiceImpl(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_USER)));
        userService.create(user);
        return user;
    }
}
