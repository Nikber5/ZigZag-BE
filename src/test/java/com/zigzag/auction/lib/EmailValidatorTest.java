package com.zigzag.auction.lib;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class EmailValidatorTest {
    private static EmailValidator emailValidator;
    @Mock
    private ConstraintValidatorContext context;

    @BeforeAll
    static void beforeAll() {
        emailValidator = new EmailValidator();
    }

    @Test
    void isValid_Ok() {
        assertTrue(emailValidator.isValid("bob@gmail.com", context));
    }

    @Test
    void isValidWithoutFirstPart_NotOk() {
        assertFalse(emailValidator.isValid("@gmail.com", context));
    }

    @Test
    void isValidWithoutSecondPart_NotOk() {
        assertFalse(emailValidator.isValid("bob@", context));
    }

    @Test
    void isValidDuplicate_NotOk() {
        assertFalse(emailValidator.isValid("bob@gmail.combob@gmail.com", context));
    }

    @Test
    void isValidWithoutEnd_NotOk() {
        assertFalse(emailValidator.isValid("bob@gmail", context));
        assertFalse(emailValidator.isValid("bob@gmail.", context));
        assertFalse(emailValidator.isValid("bob@gmail.c", context));
        assertFalse(emailValidator.isValid("bob@gmailcom", context));
    }

    @Test
    void isValidWithoutAt_NotOk() {
        assertFalse(emailValidator.isValid("bobgmail.com", context));
    }
}
