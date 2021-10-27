package com.zigzag.auction.util;

import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;

    public DataInitializer(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        // Adding all roles
        roleService.add(new Role(Role.RoleName.ROLE_ADMIN));
        roleService.add(new Role(Role.RoleName.ROLE_USER));

        // creating mock users
        User bob = new User("Bob", "bob@gmail.com", "12345");
        bob.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_USER)));

        User alice = new User("Alice", "alice@gmail.com", "12345");
        alice.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_ADMIN)));

        userService.create(bob);
        userService.create(alice);
    }
}
