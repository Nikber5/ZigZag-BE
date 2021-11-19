package com.zigzag.auction.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.zigzag.auction.dto.request.UserRegisterRequestDto;
import com.zigzag.auction.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.UserArgumentsUtil;

class RegisterMapperTest {
    private static RegisterMapper registerMapper;

    @BeforeAll
    static void beforeAll() {
        registerMapper = new RegisterMapper();
    }

    @Test
    void mapToModel_ok() {
        UserRegisterRequestDto dto = new UserRegisterRequestDto(UserArgumentsUtil.VALID_EMAIL,
                UserArgumentsUtil.VALID_PASSWORD, UserArgumentsUtil.VALID_PASSWORD,
                UserArgumentsUtil.NAME, UserArgumentsUtil.SECOND_NAME);
        User user = registerMapper.mapToModel(dto);
        assertNotNull(user);
        assertEquals(UserArgumentsUtil.NAME, user.getFirstName());
        assertEquals(UserArgumentsUtil.SECOND_NAME, user.getSecondName());
        assertEquals(UserArgumentsUtil.VALID_EMAIL, user.getEmail());
        assertEquals(UserArgumentsUtil.VALID_PASSWORD, user.getPassword());
    }

    @Test
    void mapToModelNullDto_notOk() {
        assertThrows(NullPointerException.class, () -> registerMapper.mapToModel(null));
    }

    @Test
    void mapToModelEmptyDto_ok() {
        UserRegisterRequestDto dto = new UserRegisterRequestDto();
        User user = registerMapper.mapToModel(dto);
        assertNotNull(user);
        assertNull(user.getFirstName());
        assertNull(user.getSecondName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
    }
}
