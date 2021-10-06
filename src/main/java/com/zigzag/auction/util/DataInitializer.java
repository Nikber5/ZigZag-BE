package com.zigzag.auction.util;

import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.ProductService;
import com.zigzag.auction.service.UserService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataInitializer {
    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Product iphone  = new Product("iphone", "new IOS phone");
        Product xiaomi  = new Product("xiaomi", "new android phone");
        List<Product> products = List.of(iphone, xiaomi);

        User bob = new User("Bob", "bob@gmail.com");
        bob.setProducts(products);
        User alice = new User("Alice", "alice@gmail.com");
        userService.create(bob);
        userService.create(alice);

        System.out.println(userService.get(bob.getId()));
    }
}
