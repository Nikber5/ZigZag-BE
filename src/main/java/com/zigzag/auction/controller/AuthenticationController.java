package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.UserLoginDto;
import com.zigzag.auction.dto.request.UserRegisterRequestDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.exception.AuthenticationException;
import com.zigzag.auction.exception.InvalidCredentialsException;
import com.zigzag.auction.jwt.JwtTokenProvider;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.mapper.RegisterMapper;
import com.zigzag.auction.service.mapper.UserMapper;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final UserMapper mapper;
    private final RegisterMapper registerMapper;
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(UserMapper mapper, RegisterMapper registerMapper,
                                    AuthenticationService authenticationService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.mapper = mapper;
        this.registerMapper = registerMapper;
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRegisterRequestDto requestDto) {
        if (!authenticationService.isUnique(requestDto.getEmail())) {
            throw new InvalidCredentialsException("Email must be unique");
        }
        User user = registerMapper.mapToModel(requestDto);
        authenticationService.register(user);
        return mapper.mapToDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
            throws AuthenticationException {
        User user = authenticationService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);

    }
}
