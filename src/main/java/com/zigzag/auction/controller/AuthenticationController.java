package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.UserRequestDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.mapper.UserMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final UserMapper mapper;
    private final AuthenticationService authenticationService;

    public AuthenticationController(UserMapper mapper, AuthenticationService authenticationService) {
        this.mapper = mapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody UserRequestDto requestDto) {
        User user = authenticationService.register(requestDto.getEmail(), requestDto.getPassword());
        return mapper.mapToDto(user);
    }
}
