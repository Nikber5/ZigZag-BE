package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.ProductRequestDto;
import com.zigzag.auction.dto.response.ProductResponseDto;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.ProductService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, UserService userService,
                             ProductMapper mapper) {
        this.productService = productService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public Page<ProductResponseDto> getMyProducts(Authentication auth, Pageable pageable) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findByEmail(details.getUsername());
        Page<Product> productsByUser = productService.findProductsByUser(user, pageable);
        return productsByUser.map(mapper::mapToDto);
    }

    @PostMapping
    public ProductResponseDto createProduct(Authentication auth,
                                            @RequestBody ProductRequestDto dto) {
        Product product = mapper.mapToModel(dto);
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findByEmail(details.getUsername());
        product.setOwner(user);
        productService.create(product);
        return mapper.mapToDto(product);
    }
}
