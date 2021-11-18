package com.zigzag.auction.lib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    public static final Pattern EMAIL_VALIDATION_PATTERN
            = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String field, ConstraintValidatorContext context) {
        Matcher matcher = EMAIL_VALIDATION_PATTERN.matcher(field);
        return matcher.find();
    }
}
