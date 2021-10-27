package com.zigzag.auction.service;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Deprecated
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
}
