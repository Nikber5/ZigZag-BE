package com.marber.auction.service;

import com.marber.auction.model.User;
import java.util.List;

public interface UserService {
    User get(Long id);

    List<User> getAll();

    User create(User user);

    User update(User user);

    void delete(Long id);
}
