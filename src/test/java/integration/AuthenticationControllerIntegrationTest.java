package integration;

import com.zigzag.auction.AuctionApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuctionApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
public class AuthenticationControllerIntegrationTest {
    private static final String NAME = "Alan";
    private static final String SECOND_NAME = "Johnes";
    private static final String VALID_EMAIL = "alan.jones@gmail.com";
    private static final String VALID_PASSWORD = "password";
    private static final String INVALID_EMAIL = "@gmail.com";
    @Autowired
    private MockMvc mvc;

    @Test
    public void register_ok() throws Exception {
        MockUserRegisterRequestDto user = new MockUserRegisterRequestDto(VALID_EMAIL, VALID_PASSWORD,
                VALID_PASSWORD, NAME, SECOND_NAME);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(NAME)))
                .andExpect(jsonPath("$.secondName", is(SECOND_NAME)))
                .andExpect(jsonPath("$.email", is(VALID_EMAIL)));
    }

    @Test
    public void registerWithInvalidPassword_notOk() throws Exception {
        MockUserRegisterRequestDto user = new MockUserRegisterRequestDto(INVALID_EMAIL, VALID_PASSWORD,
                VALID_PASSWORD, NAME, SECOND_NAME);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        user = new MockUserRegisterRequestDto(VALID_EMAIL, VALID_PASSWORD,
                "anotherPassword", NAME, SECOND_NAME);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
