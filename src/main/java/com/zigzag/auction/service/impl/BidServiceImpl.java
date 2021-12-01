package com.zigzag.auction.service.impl;

import com.zigzag.auction.exception.InvalidBidException;
import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.BidRepository;
import com.zigzag.auction.service.BidService;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class BidServiceImpl implements BidService {
    private final BidRepository repository;

    public BidServiceImpl(BidRepository repository) {
        this.repository = repository;
    }

    @Override
    public Bid makeABet(User user, Lot lot, BigInteger bidSum) throws InvalidBidException {
        if (bidSum.compareTo(lot.getHighestPrice()) < 1) {
            throw new InvalidBidException("Bid sum should be higher than existing one");
        }
        Bid bid = new Bid();
        bid.setBetTime(LocalDateTime.now());
        bid.setBidSum(bidSum);
        bid.setOwner(user);
        bid.setLot(lot);
        return repository.save(bid);
    }
}
