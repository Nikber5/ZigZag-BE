package com.zigzag.auction.controller;

import com.zigzag.auction.dto.response.LotResponseDto;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.BidService;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.LotMapper;
import com.zigzag.auction.util.RoleUtil;
import java.math.BigInteger;
import org.springframework.security.access.annotation.Secured;
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
    private final LotMapper mapper;

    public BidController(UserService userService, BidService bidService,
                         LotService lotService, LotMapper mapper) {
        this.userService = userService;
        this.bidService = bidService;
        this.lotService = lotService;
        this.mapper = mapper;
    }

    @PostMapping("/{lotId}")
    @Secured(RoleUtil.ROLE_USER)
    public LotResponseDto makeABet(Authentication auth, @PathVariable Long lotId,
                                   @RequestParam BigInteger bidSum) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.getUserWithProductsByEmail(details.getUsername());
        Lot lot = lotService.get(lotId);

        bidService.makeABet(user, lot, bidSum);
        return mapper.mapToDto(lot);
    }
}
