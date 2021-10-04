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
    private final ProductService productService;

    public DataInitializer(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        Product iphone  = new Product("iphone", "new IOS phone");
        Product xiaomi  = new Product("xiaomi", "new android phone");
        List<Product> products = List.of(iphone, xiaomi);
        productService.create(iphone);
        productService.create(xiaomi);

        User bob = new User("Bob", "bob@gmail.com");
        bob.setProducts(products);
        User alice = new User("Alice", "alice@gmail.com");
        userService.create(bob);
        userService.create(alice);

        System.out.println(userService.get(bob.getId()));
    }
}