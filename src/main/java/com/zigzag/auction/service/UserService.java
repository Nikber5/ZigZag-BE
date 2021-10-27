package com.zigzag.auction.service;

import com.zigzag.auction.model.User;

public interface UserService extends AbstractCRUDService<User, Long> {
    User findByEmail(String email);

    User getUserWithProductsByEmail(String email);
}
