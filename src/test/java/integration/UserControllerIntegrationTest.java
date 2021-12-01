package integration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.zigzag.auction.AuctionApplication;
import com.zigzag.auction.dto.request.UserLoginDto;
import com.zigzag.auction.dto.response.UserResponseDto;
import com.zigzag.auction.exception.DataProcessingException;
import com.zigzag.auction.model.Role;
import com.zigzag.auction.model.User;
import com.zigzag.auction.repository.UserRepository;
import com.zigzag.auction.service.RoleService;
import com.zigzag.auction.service.UserService;
import com.zigzag.auction.service.mapper.UserMapper;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuctionApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@AutoConfigureTestDatabase
public class UserControllerIntegrationTest {
    private static final String TOKEN_PREFIX = "{\"token\":\"";
    private static final String BEARER_PREFIX = "Bearer " ;

    private static UserLoginDto adminLoginDto;
    private static UserLoginDto userLoginDto;

    private static boolean setUpIsDone = false;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private Long userID;

    @Before
    public void init() {
        if (!setUpIsDone) {
            User user = new User("User", "user@gmail.com", "12345");
            user.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_USER)));

            User admin = new User("Admin", "admin@gmail.com", "12345");
            admin.setRoles(Set.of(roleService.getRoleByName(Role.RoleName.ROLE_ADMIN)));

            userService.create(user);
            userService.create(admin);
            userID = user.getId();
            setUpIsDone = true;
        }
    }

    @BeforeClass
    public static void beforeClass() {
        adminLoginDto = new UserLoginDto();
        adminLoginDto.setLogin("admin@gmail.com");
        adminLoginDto.setPassword("12345");

        userLoginDto = new UserLoginDto();
        userLoginDto.setLogin("user@gmail.com");
        userLoginDto.setPassword("12345");
    }

    @Test
    public void getAll_ok() throws Exception {
        String aliceToken = getToken(adminLoginDto);

        int count = userService.getAll().size();

        mvc.perform(get("/users").header("Authorization", BEARER_PREFIX + aliceToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id", is(1)))
                .andExpect(jsonPath("$.content[1].id", is(2)))
                .andExpect(jsonPath("$.totalElements", is(count)));
    }

    @Test
    public void get_ok() throws Exception {
        mvc.perform(get("/users/1")
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
        String aliceToken = getToken(adminLoginDto);

        mvc.perform(get("/users/by-email?email=alice@gmail.com")
                .header("Authorization", BEARER_PREFIX + aliceToken)
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
        String bobsToken = getToken(userLoginDto);

        User user = userRepository.findById(userID)
                .orElseThrow(() -> new DataProcessingException("Can't get user by id: " + userID));
        assertNull(user.getSecondName());
        user.setSecondName("Johnson");
        UserResponseDto dto = mapper.mapToDto(user);

        mvc.perform(put("/users").header("Authorization", BEARER_PREFIX + bobsToken)
                .contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(dto))).andDo(print())
                .andExpect(status().isOk());

        User updated = userRepository.findById(userID)
                .orElseThrow(() -> new DataProcessingException("Can't get user by id: " + userID));
        assertEquals(user.getSecondName(), updated.getSecondName());
    }

    @Test
    public void delete_ok() throws Exception {
        String aliceToken = getToken(adminLoginDto);

        User john = new User();
        john.setFirstName("John");
        john.setFirstName("Johnson");
        User save = userRepository.save(john);
        List<User> oldUsers = userRepository.findAll();

        mvc.perform(delete("/users/" + save.getId())
                .header("Authorization", BEARER_PREFIX + aliceToken)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

        List<User> newUsers = userRepository.findAll();
        assertEquals(oldUsers.size(), newUsers.size() + 1);
    }

    private String getToken(UserLoginDto dto) throws Exception {
        MvcResult mvcResult = mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(dto))).andDo(print()).andExpect(status().isOk()).andReturn();
        String tokenString = mvcResult.getResponse().getContentAsString();
        return tokenString
                .substring(tokenString.indexOf(TOKEN_PREFIX) + TOKEN_PREFIX.length(), tokenString.length() - 2);
    }
}
