package com.zigzag.auction.lib;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import integration.MockUserRegisterRequestDto;
import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class FieldsValueMatchValidatorTest {
    private static final String NAME = "Alan";
    private static final String SECOND_NAME = "Johnes";
    private static final String VALID_EMAIL = "alan.jones@gmail.com";
    private static final String VALID_PASSWORD = "password";

    private static FieldsValueMatchValidator valueMatchValidator;
    @Mock
    private ConstraintValidatorContext context;

    @BeforeAll
    static void beforeAll() {
        valueMatchValidator = new FieldsValueMatchValidator();
    }

    @BeforeEach
    void setUp() {
        FieldsValueMatch mock = mock(FieldsValueMatch.class);
        when(mock.field()).thenReturn("password");
        when(mock.fieldMatch()).thenReturn("repeatPassword");
        valueMatchValidator.initialize(mock);
    }

    @Test
    void isValidTest_ok() {
        MockUserRegisterRequestDto user = new MockUserRegisterRequestDto(VALID_EMAIL, VALID_PASSWORD,
                VALID_PASSWORD, NAME, SECOND_NAME);
        assertTrue(valueMatchValidator.isValid(user, context));
    }

    @Test
    void isValidTestPasswordDoesntMatch_notOk() {
        MockUserRegisterRequestDto user = new MockUserRegisterRequestDto(VALID_EMAIL, VALID_PASSWORD.substring(0, 2),
                VALID_PASSWORD, NAME, SECOND_NAME);
        assertFalse(valueMatchValidator.isValid(user, context));
    }

    @Test
    void isValidTestPasswordsNull_ok() {
        MockUserRegisterRequestDto user = new MockUserRegisterRequestDto(VALID_EMAIL, null,
                null, NAME, SECOND_NAME);
        assertTrue(valueMatchValidator.isValid(user, context));
    }

    @Test
    void isValidTestOnePasswordisNull_ok() {
        MockUserRegisterRequestDto user = new MockUserRegisterRequestDto(VALID_EMAIL, VALID_PASSWORD,
                null, NAME, SECOND_NAME);
        assertFalse(valueMatchValidator.isValid(user, context));
        user = new MockUserRegisterRequestDto(VALID_EMAIL, null, VALID_PASSWORD,
                NAME, SECOND_NAME);
        assertFalse(valueMatchValidator.isValid(user, context));
    }
}
