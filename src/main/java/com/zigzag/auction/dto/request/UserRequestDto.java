package com.zigzag.auction.dto.request;

public class UserRequestDto {
    private String email;
    private String password;
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
