package integration;

import com.zigzag.auction.lib.FieldsValueMatch;
import com.zigzag.auction.lib.ValidEmail;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class MockUserRegisterRequestDto {
    @ValidEmail
    private String email;
    @Size(min = 8, max = 40)
    @NotNull
    private String password;
    @NotNull
    private String repeatPassword;
    private String firstName;
    private String secondName;

    public MockUserRegisterRequestDto() {
    }

    public MockUserRegisterRequestDto(String email, String password, String repeatPassword, String firstName, String secondName) {
        this.email = email;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.firstName = firstName;
        this.secondName = secondName;
    }

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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
