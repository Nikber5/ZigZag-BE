package com.marber.auction.util;

import com.marber.auction.model.User;
import com.marber.auction.service.UserService;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class DataInitializer {
    private final UserService userService;

    public DataInitializer(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        userService.create(new User("Bob", "bob@gmail.com"));
        userService.create(new User("Alice", "alice@gmail.com"));
    }
}
