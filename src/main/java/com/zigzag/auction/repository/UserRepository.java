package com.zigzag.auction.repository;

import com.zigzag.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
