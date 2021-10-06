package com.zigzag.auction.service;

import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.ProductRepository;
import com.zigzag.auction.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        List<Product> products = user.getProducts();
        if (products != null) {
            products.forEach(productRepository::save);
        }
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        List<Product> products = user.getProducts();
        if (products != null) {
            products.forEach(productRepository::save);
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = get(id);
        user.getProducts().forEach(productRepository::delete);
        userRepository.deleteById(id);
    }
}
