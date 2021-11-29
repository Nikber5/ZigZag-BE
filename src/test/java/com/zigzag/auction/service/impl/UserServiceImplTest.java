package com.zigzag.auction.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.UserRepository;
import com.zigzag.auction.service.UserService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;
import util.UserArgumentsUtil;

class UserServiceImplTest {
    private static UserService userService;
    private static UserRepository userRepository;
    private static User expected;

    @BeforeAll
    static void beforeAll() {
        PasswordEncoder encoder = mock(PasswordEncoder.class);
        userRepository = mock(UserRepository.class);

        expected = new User(UserArgumentsUtil.NAME, UserArgumentsUtil.VALID_EMAIL,
                UserArgumentsUtil.ENCODED_PASSWORD);
        expected.setSecondName(UserArgumentsUtil.SECOND_NAME);

        when(encoder.encode(any(String.class))).thenReturn(UserArgumentsUtil.ENCODED_PASSWORD);
        when(userRepository.findById(1L)).thenReturn(Optional.of(expected));
        when(userRepository.findAll()).thenReturn(List.of(expected));
        when(userRepository.save(any(User.class))).thenReturn(expected);
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(expected));
        when(userRepository.getUserWithProductsByEmail(any(String.class)))
                .thenReturn(Optional.of(expected));

        userService = new UserServiceImpl(encoder, userRepository);
    }

    @Test
    void getWithValidUser_ok() {
        User actual = userService.get(1L);
        assertNotNull(actual);
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getSecondName(), actual.getSecondName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    void getWithNullUser_notOk() {
        assertThrows(DataProcessingException.class, () -> userService.get(null));

        try {
            userService.get(null);
        } catch (DataProcessingException e) {
            String expected = String.format("Can't get user by id: %s", "null");
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    void getAll_ok() {
        List<User> actual = userService.getAll();
        assertNotNull(actual);
        assertEquals(List.of(expected), actual);
    }

    @Test
    void createWithValidUser_ok() {
        User user = new User(UserArgumentsUtil.NAME, UserArgumentsUtil.VALID_EMAIL,
                UserArgumentsUtil.VALID_PASSWORD);
        User actual = userService.create(user);
        assertNotNull(actual);
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getEmail(), actual.getEmail());
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void updateWithValidUser_ok() {
        User user = new User(UserArgumentsUtil.NAME, UserArgumentsUtil.VALID_EMAIL,
                UserArgumentsUtil.ENCODED_PASSWORD);
        User actual = userService.update(user);
        assertNotNull(actual);
        assertEquals(user.getFirstName(), actual.getFirstName());
        assertEquals(user.getEmail(), actual.getEmail());
        assertEquals(user.getPassword(), actual.getPassword());
    }

    @Test
    void delete_ok() {
        userService.delete(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void findByEmail_ok() {
        User actual = userService.findByEmail(UserArgumentsUtil.VALID_EMAIL);
        assertNotNull(actual);
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getSecondName(), actual.getSecondName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    void findByEmailNull_notOk() {
        assertThrows(DataProcessingException.class, () -> userService.findByEmail(null));

        try {
            userService.findByEmail(null);
        } catch (DataProcessingException e) {
            String expected = String.format("Can't get user by email: %s", "null");
            assertEquals(expected, e.getMessage());
        }
    }

    @Test
    void getUserWithProductsByEmail_ok() {
        User actual = userService.getUserWithProductsByEmail(UserArgumentsUtil.VALID_EMAIL);
        assertNotNull(actual);
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getSecondName(), actual.getSecondName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    @Test
    void getUserWithProductsByEmailNullEmail_notOk() {
        assertThrows(DataProcessingException.class, () -> userService.getUserWithProductsByEmail(null));

        try {
            userService.getUserWithProductsByEmail(null);
        } catch (DataProcessingException e) {
            String expected = String.format("Can't get user by email with products: %s", "null");
            assertEquals(expected, e.getMessage());
        }
    }
}
