package com.zigzag.auction.util;

import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer {
    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationService authenticationService;

    public DataInitializer(UserService userService, RoleService roleService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    public void init() {
        roleService.add(new Role(Role.RoleName.ROLE_ADMIN));
        roleService.add(new Role(Role.RoleName.ROLE_USER));

        Bid bid = new Bid(LocalDateTime.now(), BigInteger.valueOf(100L));


        Product iphone = new Product("iphone", "new IOS phone");
        Product xiaomi = new Product("xiaomi", "new android phone");
        List<Product> products = List.of(iphone, xiaomi);

        User bob = new User("Bob", "bob@gmail.com", "12345");
        //bid.setOwner(bob);
        Role roleByName = roleService.getRoleByName(Role.RoleName.ROLE_USER);
        bob.setRoles(Set.of(roleByName));
        bob.setProducts(products);
        bob.setBids(Set.of(bid));
        User alice = new User("Alice", "alice@gmail.com", "12345");
        alice.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_ADMIN)));
        userService.create(bob);
        userService.create(alice);

        System.out.println(userService.get(bob.getId()));
        //userService.delete(bob.getId());
        authenticationService.register("john@gmail.com", "11111");
    }
}
