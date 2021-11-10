package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.UserRequestDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.LotMapper;
import com.zigzag.auction.service.mapper.ProductMapper;
import com.zigzag.auction.service.mapper.UserMapper;
import com.zigzag.auction.util.RoleUtil;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final ProductMapper productMapper;
    private final LotMapper lotMapper;

    public UserController(UserService userService, UserMapper mapper,
                          ProductMapper productMapper, LotMapper lotMapper) {
        this.userService = userService;
        this.userMapper = mapper;
        this.productMapper = productMapper;
        this.lotMapper = lotMapper;
    }

    @GetMapping
    @Secured(RoleUtil.ROLE_ADMIN)
    public List<UserResponseDto> getAll() {
        return userService.getAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserResponseDto get(@PathVariable Long id) {
        return mapToDto(userService.get(id));
    }

    @PutMapping("/update")
    @Secured(RoleUtil.ROLE_USER)
    public UserResponseDto update(@RequestBody UserRequestDto dto, Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findByEmail(details.getUsername());
        updateUserDescriptionFromDto(user, dto);
        return mapToDto(userService.update(user));

    }

    @DeleteMapping
    @Secured(RoleUtil.ROLE_ADMIN)
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "Done";
    }

    @GetMapping("/by-email")
    @Secured(RoleUtil.ROLE_ADMIN)
    public UserResponseDto getByEmail(@RequestParam String email) {
        System.out.println("Getting user by email " + email);
        return mapToDto(userService.findByEmail(email));
    }

    private void updateUserDescriptionFromDto(User user, UserRequestDto dto) {
        if (StringUtils.hasLength(dto.getFirstName())) {
            user.setFirstName(dto.getFirstName());
        }
        if (StringUtils.hasLength(dto.getSecondName())) {
            user.setSecondName(dto.getSecondName());
        }
    }

    private UserResponseDto mapToDto(User user) {
        UserResponseDto dto = userMapper.mapToDto(user);
        dto.setLots(user.getLots().stream()
                .map(lotMapper::mapToDto)
                .collect(Collectors.toList()));
        dto.setProducts(user.getProducts().stream()
                .map(productMapper::mapToDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
