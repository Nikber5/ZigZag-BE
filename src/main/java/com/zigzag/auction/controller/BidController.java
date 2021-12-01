package com.zigzag.auction.controller;

import com.zigzag.auction.dto.response.BidResponseDto;
import com.zigzag.auction.exception.RequestValidationException;
import com.zigzag.auction.model.Bid;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.BidService;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.BidMapper;
import java.math.BigInteger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bids")
public class BidController {
    private final UserService userService;
    private final BidService bidService;
    private final LotService lotService;
    private final BidMapper mapper;

    public BidController(UserService userService, BidService bidService,
                         LotService lotService, BidMapper mapper) {
        this.userService = userService;
        this.bidService = bidService;
        this.lotService = lotService;
        this.mapper = mapper;
    }

    @PostMapping("/{lotId}")
    public BidResponseDto makeABet(Authentication auth, @PathVariable Long lotId,
                                   @RequestParam BigInteger bidSum) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.getUserWithProductsByEmail(details.getUsername());
        Lot lot = lotService.get(lotId);
        if (user.getEmail().equals(lot.getCreator().getEmail())) {
            throw new RequestValidationException("User can't make a bet on an own lot");
        }

        Bid bid = bidService.makeABet(user, lot, bidSum);
        return mapper.mapToDto(bid);
    }
}
