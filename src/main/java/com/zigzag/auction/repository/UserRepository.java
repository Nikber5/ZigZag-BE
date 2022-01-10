package com.zigzag.auction.repository;

import com.zigzag.auction.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.products "
            + "LEFT JOIN FETCH u.lots LEFT JOIN FETCH u.bids "
            + "LEFT JOIN FETCH u.likedLots WHERE u.id = :id")
    Optional<User> findFullUserInfoById(Long id);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.products "
            + "LEFT JOIN FETCH u.lots LEFT JOIN FETCH u.bids "
            + "LEFT JOIN FETCH u.likedLots WHERE u.email = :email")
    Optional<User> findFullUserInfoByEmail(String email);
}
