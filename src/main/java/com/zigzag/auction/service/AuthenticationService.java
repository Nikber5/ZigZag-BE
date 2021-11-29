package com.zigzag.auction.service;

import com.zigzag.auction.exception.AuthenticationException;
import com.zigzag.auction.model.User;

public interface AuthenticationService {
    User register(User user);

    User login(String login, String password) throws AuthenticationException;
}
