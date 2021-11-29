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
import util.UserArgumentsUtil;

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
    @Autowired
    private MockMvc mvc;

    @Test
    public void register_ok() throws Exception {
        MockUserRegisterRequestDto user
                = new MockUserRegisterRequestDto(UserArgumentsUtil.VALID_EMAIL, UserArgumentsUtil.VALID_PASSWORD,
                UserArgumentsUtil.VALID_PASSWORD, UserArgumentsUtil.NAME, UserArgumentsUtil.SECOND_NAME);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName", is(UserArgumentsUtil.NAME)))
                .andExpect(jsonPath("$.secondName", is(UserArgumentsUtil.SECOND_NAME)))
                .andExpect(jsonPath("$.email", is(UserArgumentsUtil.VALID_EMAIL)));
    }

    @Test
    public void registerWithInvalidPassword_notOk() throws Exception {
        MockUserRegisterRequestDto user
                = new MockUserRegisterRequestDto(UserArgumentsUtil.INVALID_EMAIL, UserArgumentsUtil.VALID_PASSWORD,
                UserArgumentsUtil.VALID_PASSWORD, UserArgumentsUtil.NAME, UserArgumentsUtil.SECOND_NAME);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        user = new MockUserRegisterRequestDto(UserArgumentsUtil.VALID_EMAIL, UserArgumentsUtil.VALID_PASSWORD,
                "anotherPassword", UserArgumentsUtil.NAME, UserArgumentsUtil.SECOND_NAME);

        mvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
