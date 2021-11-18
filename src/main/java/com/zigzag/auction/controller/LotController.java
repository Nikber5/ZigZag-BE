package com.zigzag.auction.controller;

import com.zigzag.auction.dto.response.LotResponseDto;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.LotMapper;
import com.zigzag.auction.util.RoleUtil;
import com.zigzag.auction.util.TimeUtil;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lots")
public class LotController {
    private final UserService userService;
    private final LotService lotService;
    private final LotMapper mapper;

    public LotController(UserService userService, LotService lotService, LotMapper mapper) {
        this.userService = userService;
        this.lotService = lotService;
        this.mapper = mapper;
    }

    @GetMapping
    public List<LotResponseDto> getAll() {
        return lotService.getAll().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public LotResponseDto get(@PathVariable Long id) {
        return mapper.mapToDto(lotService.get(id));
    }

    @PostMapping
    @Secured(RoleUtil.ROLE_USER)
    public LotResponseDto createLot(Authentication auth, @RequestParam Long productId,
                                    @RequestParam BigInteger startPrice) {
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
        return mapper.mapToDto(lotService.create(lot));
    }
}
