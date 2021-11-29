package com.zigzag.auction.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.service.AuthenticationService;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import util.UserArgumentsUtil;

class AuthenticationServiceImplTest {
    private static AuthenticationService authenticationService;

    private static User expected;

    @BeforeAll
    static void beforeAll() {
        expected = new User(UserArgumentsUtil.NAME, UserArgumentsUtil.VALID_EMAIL,
                UserArgumentsUtil.VALID_PASSWORD);
        expected.setId(1L);

        UserService userService = mock(UserService.class);
        RoleService roleService = mock(RoleService.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        when(roleService.getRoleByName(Role.RoleName.ROLE_USER))
                .thenReturn(new Role(Role.RoleName.ROLE_USER));
        when(userService.create(any(User.class))).thenReturn(expected);
        authenticationService = new AuthenticationServiceImpl(userService, roleService, passwordEncoder);
    }

    @Test
    void register_ok() {
        User user = new User(UserArgumentsUtil.NAME, UserArgumentsUtil.VALID_EMAIL,
                UserArgumentsUtil.VALID_PASSWORD);

        User actual = authenticationService.register(user);
        assertNotNull(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getSecondName(), actual.getSecondName());
        assertEquals(expected.getPassword(), actual.getPassword());
    }
}
