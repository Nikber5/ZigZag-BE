package com.zigzag.auction.controller;

import com.zigzag.auction.dto.response.LotResponseDto;
import com.zigzag.auction.model.Lot;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.LotService;
import com.zigzag.auction.service.ProductService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.LotMapper;
import com.zigzag.auction.util.TimeUtil;
import java.math.BigInteger;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final ProductService productService;
    private final LotMapper mapper;

    public LotController(UserService userService, LotService lotService,
                         ProductService productService, LotMapper mapper) {
        this.userService = userService;
        this.lotService = lotService;
        this.productService = productService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<LotResponseDto> getAll(Pageable pageable) {
        return lotService.getAllWithPagination(pageable).map(mapper::mapToDto);
    }

    @GetMapping("/{id}")
    public LotResponseDto get(@PathVariable Long id) {
        return mapper.mapToDto(lotService.get(id));
    }

    @PostMapping
    public LotResponseDto createLot(Authentication auth, @RequestParam Long productId,
                                    @RequestParam BigInteger startPrice) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findByEmail(details.getUsername());
        Product product = productService.get(productId);
        if (product.getOwner() == null || !product.getOwner().getEmail().equals(user.getEmail())) {
            throw new RuntimeException("User don't have product with id: " + productId);
        }
        product.setOwner(null);
        productService.update(product);
        Lot lot = new Lot(user, product, LocalDateTime.now(),
                LocalDateTime.now().plusDays(TimeUtil.DEFAULT_LOT_DURATION_DAYS),
                startPrice, startPrice, true);
        return mapper.mapToDto(lotService.create(lot));
    }
}
