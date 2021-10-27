package com.zigzag.auction.service.mapper;

import com.zigzag.auction.dto.request.UserRequestDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements RequestDtoMapper<UserRequestDto, User>,
        ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public User mapToModel(UserRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setFirstName(dto.getFirstName());
        user.setSecondName(dto.getSecondName());
        return user;
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setSecondName(user.getSecondName());
        return dto;
    }
}
