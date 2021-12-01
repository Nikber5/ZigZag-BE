package com.zigzag.auction.service;

import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService extends AbstractCrudService<Product, Long> {
    Page<Product> findProductsByUser(User user, Pageable pageable);
}
