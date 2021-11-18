package com.zigzag.auction.dto.request;

import com.zigzag.auction.lib.FieldsValueMatch;
import com.zigzag.auction.lib.ValidEmail;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegisterRequestDto {
    @ValidEmail
    private String email;
    @Size(min = 8, max = 40)
    @NotNull
    private String password;
    @NotNull
    private String repeatPassword;
    private String firstName;
    private String secondName;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }
}
