package com.zigzag.auction.repository;

import com.zigzag.auction.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
