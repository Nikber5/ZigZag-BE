package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.UserRequestDto;
import com.zigzag.auction.dto.response.EagerUserResponseDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.lib.ApiPageable;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.EagerUserMapper;
import com.zigzag.auction.service.mapper.UserMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final EagerUserMapper eagerUserMapper;

    public UserController(UserService userService, UserMapper userMapper, EagerUserMapper eagerUserMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.eagerUserMapper = eagerUserMapper;
    }

    @GetMapping
    @ApiOperation(value = "Returns all users.",
            notes = "Method is only for an admin as for now.")
    @ApiPageable
    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userService.getAllWithPagination(pageable).map(userMapper::mapToDto);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Returns user by id.",
            notes = "Method permits all users.")
    public EagerUserResponseDto get(@PathVariable Long id) {
        return eagerUserMapper.mapToDto(userService.get(id));
    }

    @PutMapping
    @ApiOperation(value = "Method to update user info.",
            notes = "User must be authenticated, and can update his first and second name.")
    public UserResponseDto update(@RequestBody UserRequestDto dto, Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findByEmail(details.getUsername());
        updateUserDescriptionFromDto(user, dto);
        return userMapper.mapToDto(userService.update(user));

    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deletes a user.",
            notes = "Method for an admin.")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "Done";
    }

    @GetMapping("/by-email")
    @ApiOperation(value = "Gets user by email.",
            notes = "Method for an admin.")
    public EagerUserResponseDto getByEmail(@RequestParam String email) {
        return eagerUserMapper.mapToDto(userService.findUserWithProductsLotsAndBidsByEmail(email));
    }

    private void updateUserDescriptionFromDto(User user, UserRequestDto dto) {
        if (StringUtils.hasLength(dto.getFirstName())) {
            user.setFirstName(dto.getFirstName());
        }
        if (StringUtils.hasLength(dto.getSecondName())) {
            user.setSecondName(dto.getSecondName());
        }
    }
}
