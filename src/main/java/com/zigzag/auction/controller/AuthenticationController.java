package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.UserRegisterRequestDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.mapper.RegisterMapper;
import com.zigzag.auction.service.mapper.UserMapper;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final UserMapper mapper;
    private final RegisterMapper registerMapper;
    private final AuthenticationService authenticationService;

    public AuthenticationController(UserMapper mapper, RegisterMapper registerMapper,
                                    AuthenticationService authenticationService) {
        this.mapper = mapper;
        this.registerMapper = registerMapper;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRegisterRequestDto requestDto) {
        User user = registerMapper.mapToModel(requestDto);
        authenticationService.register(user);
        return mapper.mapToDto(user);
    }
}
