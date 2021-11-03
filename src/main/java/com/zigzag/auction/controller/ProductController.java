package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.ProductRequestDto;
import com.zigzag.auction.dto.response.ProductResponseDto;
import com.zigzag.auction.model.Product;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.ProductService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.ProductMapper;
import com.zigzag.auction.util.RoleUtil;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final UserService userService;
    private final ProductMapper mapper;

    public ProductController(ProductService productService, UserService userService, ProductMapper mapper) {
        this.productService = productService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    @Secured(RoleUtil.ROLE_USER)
    public List<ProductResponseDto> getMyProducts(Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.getUserWithProductsByEmail(details.getUsername());
        return user.getProducts().stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/new")
    @Secured(RoleUtil.ROLE_USER)
    public ProductResponseDto createProduct(Authentication auth, @RequestBody ProductRequestDto dto) {
        Product product = mapper.mapToModel(dto);
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.getUserWithProductsByEmail(details.getUsername());
        productService.create(product);
        user.getProducts().add(product);
        userService.update(user);
        return mapper.mapToDto(product);
    }
}
