package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zigzag.auction.AuctionApplication;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.UserRepository;
import com.zigzag.auction.service.mapper.UserMapper;
import java.util.List;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuctionApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
public class UserControllerIntegrationTest {
    private static final String ADMIN_AUTHORIZATION = "Basic YWxpY2VAZ21haWwuY29tOjEyMzQ1";
    private static final String USER_AUTHORIZATION = "Basic Ym9iQGdtYWlsLmNvbToxMjM0NQ==";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper mapper;

    @Test
    public void getAll_ok() throws Exception {
        mvc.perform(get("/users").header("Authorization", ADMIN_AUTHORIZATION)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)));
    }

    @Test
    public void get_ok() throws Exception {
        mvc.perform(get("/users/1").header("Authorization", ADMIN_AUTHORIZATION)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("Bob")));
    }

    @Test
    public void getByEmail_ok() throws Exception {
        mvc.perform(get("/users/by-email?email=alice@gmail.com")
                .header("Authorization", ADMIN_AUTHORIZATION)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.firstName", is("Alice")));
    }

    @Test
    public void update_ok() throws Exception {
        User bob = userRepository.findById(1L)
                .orElseThrow(() -> new DataProcessingException("Can't get user by id: " + 1L));
        assertNull(bob.getSecondName());
        bob.setSecondName("Johnson");
        UserResponseDto dto = mapper.mapToDto(bob);

        mvc.perform(put("/users").header("Authorization", USER_AUTHORIZATION)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(dto))).andDo(print())
                .andExpect(status().isOk());

        User updated = userRepository.findById(1L).get();
        assertEquals(bob.getSecondName(), updated.getSecondName());
    }

    @Test
    public void delete_ok() throws Exception {
        User john = new User();
        john.setFirstName("John");
        john.setFirstName("Johnson");
        User save = userRepository.save(john);
        List<User> all = userRepository.findAll();
        assertEquals(3, all.size());

        mvc.perform(delete("/users/" + save.getId()).header("Authorization", ADMIN_AUTHORIZATION)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        all = userRepository.findAll();
        assertEquals(2, all.size());
    }
}
