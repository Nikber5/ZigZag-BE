package com.zigzag.auction.repository;

import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByOwner(User user, Pageable pageable);
}
