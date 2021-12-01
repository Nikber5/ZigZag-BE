package com.zigzag.auction.service;

import com.zigzag.auction.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService extends AbstractCrudService<User, Long> {
    User findByEmail(String email);

    User getUserWithProductsByEmail(String email);

    Page<User> getAllWithPagination(Pageable pageable);
}
