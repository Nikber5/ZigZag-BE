package com.zigzag.auction.service.impl;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.ProductRepository;
import com.zigzag.auction.service.ProductService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product get(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new DataProcessingException("Can't get product by id: " + id));
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product create(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> findProductsByUser(User user, Pageable pageable) {
        return productRepository.findAllByOwner(user, pageable);
    }
}
