package com.zigzag.auction.service.mapper;

import com.zigzag.auction.dto.request.UserRegisterRequestDto;
import com.zigzag.auction.model.User;
import org.springframework.stereotype.Component;

@Component
public class RegisterMapper implements RequestDtoMapper<UserRegisterRequestDto, User> {
    @Override
    public User mapToModel(UserRegisterRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        return user;
    }
}
