package com.zigzag.auction.controller;

import com.zigzag.auction.dto.request.UserRequestDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.UserMapper;
import com.zigzag.auction.util.RoleUtil;
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
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper mapper;

    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    @Secured(RoleUtil.ROLE_ADMIN)
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PutMapping("/update")
    @Secured(RoleUtil.ROLE_USER)
    public UserResponseDto update(@RequestBody UserRequestDto dto, Authentication auth) {
        UserDetails details = (UserDetails) auth.getPrincipal();
        User user = userService.findByEmail(details.getUsername());
        updateUserDescriptionFromDto(user, dto);
        return mapper.mapToDto(userService.update(user));

    }

    private void updateUserDescriptionFromDto(User user, UserRequestDto dto) {
        if (StringUtils.hasLength(dto.getFirstName())) {
            user.setFirstName(dto.getFirstName());
        }
        if (StringUtils.hasLength(dto.getSecondName())) {
            user.setSecondName(dto.getSecondName());
        }
    }

    @DeleteMapping
    @Secured(RoleUtil.ROLE_ADMIN)
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "Done";
    }

    @GetMapping("/by-email")
    @Secured(RoleUtil.ROLE_ADMIN)
    public User getByEmail(@RequestParam String email) {
        System.out.println("Getting user by email " + email);
        return userService.findByEmail(email);
    }
}
