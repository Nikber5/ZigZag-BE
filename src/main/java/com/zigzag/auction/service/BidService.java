package com.zigzag.auction.service;

import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.User;
import java.math.BigInteger;

public interface BidService {
    Bid makeABet(User user, Lot lot, BigInteger bidSum);
}
