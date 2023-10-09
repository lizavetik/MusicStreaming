package com.tms.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tms.domain.UserInfo;
import com.tms.security.filter.JwtAuthenticationFilter;
import com.tms.service.UserService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    static List<UserInfo> users = new ArrayList<>();
    static UserInfo userInfo = new UserInfo();

    @BeforeAll
    public static void beforeAll() {
        userInfo.setId(5);
        users.add(userInfo);
    }
    @Test
    public void getUsersTest() throws Exception {
        Mockito.when(userService.getUsers()).thenReturn(users);
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(5)));
    }

    @Test
    public void createUserTest() throws Exception {
        UserService mockUS = Mockito.mock(UserService.class);
        Mockito.doNothing().when(mockUS).createUser(any());

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfo)))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateUserTest() throws Exception {
        UserService mockUS = Mockito.mock(UserService.class);
        Mockito.doNothing().when(mockUS).updateUser(any());

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userInfo)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteUserTest() throws Exception {
        UserService mockUS = Mockito.mock(UserService.class);
        Mockito.doNothing().when(mockUS).deleteUserById(anyInt());

        mockMvc.perform(delete("/user/10")).andExpect(status().isNoContent());
    }
}

