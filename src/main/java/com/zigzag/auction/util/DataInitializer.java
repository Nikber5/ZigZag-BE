package com.zigzag.auction.util;

import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.ProductService;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final ProductService productService;

    public DataInitializer(UserService userService, RoleService roleService,
                           ProductService productService) {
        this.userService = userService;
        this.roleService = roleService;
        this.productService = productService;
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

        for (int i = 1; i < 41; i++) {
            User user = userService.findByEmail(bob.getEmail());
            Product product = new Product();
            product.setName("New product # " + i);
            product.setDescription("Description for product #" + i);
            product.setOwner(bob);
            productService.create(product);

        }
    }
}
