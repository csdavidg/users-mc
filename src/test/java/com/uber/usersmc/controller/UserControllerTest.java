package com.uber.usersmc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.usersmc.entity.ErrorResponse;
import com.uber.usersmc.entity.User;
import com.uber.usersmc.service.UserService;
import org.hibernate.PropertyNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @Before
    public void setUp() {
        user = User.builder()
                .id(1L)
                .firstName("Cristian")
                .lastName("Sanchez")
                .phoneNumber(1523456)
                .build();
    }

    @Test
    public void testSaveUser() throws Exception {
        given(userService.createUser(user)).willReturn(user);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/user/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(user));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(user)));
    }

    @Test
    public void testGettingUser() throws Exception {
        given(userService.getUserById(1L)).willReturn(user);
        RequestBuilder request = MockMvcRequestBuilders.get("/user/1");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(user)));
    }

    @Test
    public void testGettingUserError() throws Exception {
        given(userService.getUserById(1L)).willThrow(new PropertyNotFoundException("User not found"));
        RequestBuilder request = MockMvcRequestBuilders.get("/user/1");

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("User not found")
                .build();

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(errorResponse)));
    }

    @Test
    public void testGettingAllUsers() throws Exception {
        given(userService.getAllUsers()).willReturn(Collections.singletonList(user));
        RequestBuilder request = MockMvcRequestBuilders.get("/user/all");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(Collections.singletonList(user))));
    }


}
