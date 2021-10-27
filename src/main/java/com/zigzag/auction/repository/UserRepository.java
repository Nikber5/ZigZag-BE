package com.zigzag.auction.repository;

import com.zigzag.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u left join fetch u.products WHERE u.email = :email")
    Optional<User> getUserWithProductsByEmail(String email);
}
