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

    public String getName(User user) {
        String name = "";
        String firstName = user.getFirstName();
        if (firstName != null && !firstName.equals("")) {
            name += firstName + " ";
        }
        String secondName = user.getSecondName();
        if (secondName != null && !secondName.equals("")) {
            name += secondName;
        }
        return name.equals("") ? null : name.trim();
    }
}
