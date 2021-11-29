package com.zigzag.auction.service.impl;

import com.zigzag.auction.exception.AuthenticationException;
import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userService, RoleService roleService,
                                     PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(User user) {
        user.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_USER)));
        user = userService.create(user);
        return user;
    }

    @Override
    public User login(String login, String password) throws AuthenticationException {
        User user = userService.findByEmail(login);
        String encodedPassword = passwordEncoder.encode(password);
        if (user == null || user.getPassword().equals(encodedPassword)) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return user;
    }

    @Override
    public boolean isUnique(String login) {
        try {
            User user = userService.findByEmail(login);
            return user == null;
        } catch (DataProcessingException e) {
            return true;
        }
    }
}
