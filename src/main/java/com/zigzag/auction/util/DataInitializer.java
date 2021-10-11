package com.zigzag.auction.util;

import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
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

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Bid bid = new Bid(LocalDateTime.now(), BigInteger.valueOf(100L));


        Product iphone  = new Product("iphone", "new IOS phone");
        Product xiaomi  = new Product("xiaomi", "new android phone");
        List<Product> products = List.of(iphone, xiaomi);

        User bob = new User("Bob", "bob@gmail.com");
        //bid.setOwner(bob);
        bob.setProducts(products);
        bob.setBids(Set.of(bid));
        User alice = new User("Alice", "alice@gmail.com");
        userService.create(bob);
        userService.create(alice);

        System.out.println(userService.get(bob.getId()));
        userService.delete(bob.getId());
    }
}
