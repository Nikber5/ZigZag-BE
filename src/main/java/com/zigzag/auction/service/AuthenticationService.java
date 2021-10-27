package com.zigzag.auction.service;

import com.zigzag.auction.model.User;

public interface AuthenticationService {
    User register(String email, String password);
}