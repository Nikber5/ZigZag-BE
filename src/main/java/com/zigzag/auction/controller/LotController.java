package com.zigzag.auction.controller;

import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.util.RoleUtil;
import com.zigzag.auction.util.TimeUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final UserService userService;
    private final LotService lotService;

    public LotController(UserService userService, LotService lotService) {
        this.userService = userService;
        this.lotService = lotService;
    }

    @PostMapping("/create")
    @Secured(RoleUtil.ROLE_USER)
    public Lot createLot(Authentication auth, @RequestParam Long productId, @RequestParam BigInteger startPrice) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.getUserWithProductsByEmail(details.getUsername());
        Optional<Product> productOptional = user.getProducts().stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
        if (productOptional.isEmpty()) {
            throw new RuntimeException("User don't have product with id: " + productId);
        }
        Product product = productOptional.get();
        user.getProducts().remove(product);
        userService.update(user);
        Lot lot = new Lot(user, product, LocalDateTime.now(),
                LocalDateTime.now().plusDays(TimeUtil.DEFAULT_LOT_DURATION_DAYS), startPrice, true);
        return lotService.create(lot);
    }
}
