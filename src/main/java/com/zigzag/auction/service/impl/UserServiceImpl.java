package com.zigzag.auction.service.impl;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.UserRepository;
import com.zigzag.auction.service.UserService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserServiceImpl(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    @Override
    public User get(Long id) {
        return userRepository.findUserByIdWithProductsLotsAndBids(id)
                .orElseThrow(() -> new DataProcessingException("Can't get user by id: " + id));
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new DataProcessingException("Can't get user by email: " + email));
    }

    @Override
    public User findUserWithProductsLotsAndBidsByEmail(String email) {
        return userRepository.findUserWithProductsLotsAndBidsByEmail(email)
                .orElseThrow(() -> new DataProcessingException("Can't get user by email with products: " + email));
    }

    @Override
    public Page<User> getAllWithPagination(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
