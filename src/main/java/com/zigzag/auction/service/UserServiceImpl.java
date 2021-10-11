package com.zigzag.auction.service;

import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.BidRepository;
import com.zigzag.auction.repository.ProductRepository;
import com.zigzag.auction.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final BidRepository bidRepository;

    public UserServiceImpl(UserRepository userRepository,
                           ProductRepository productRepository,
                           BidRepository bidRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.bidRepository = bidRepository;
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
        userRepository.save(user);
        Set<Bid> bids = user.getBids();
        if (bids != null) {
            bids.forEach(b -> b.setOwner(user));
            bids.forEach(bidRepository::save);
        }
        return user;
    }

    @Override
    public User update(User user) {
        userRepository.save(user);
        Set<Bid> bids = user.getBids();
        if (bids != null) {
            bids.forEach(b -> b.setOwner(user));
            bids.forEach(bidRepository::save);
        }
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
