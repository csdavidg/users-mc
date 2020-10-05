package com.uber.usersmc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uber.usersmc.entity.ErrorResponse;
import com.uber.usersmc.entity.Trip;
import com.uber.usersmc.service.TripService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TripService tripService;

    private Trip trip;

    @BeforeEach
    public void setUp() {
        trip = Trip.builder()
                .id(1L)
                .origin("home")
                .destiny("work")
                .build();
    }

    @Test
    public void testSaveTrip() throws Exception {
        given(tripService.createTrip(trip)).willReturn(trip);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/trip/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(trip));

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(trip)));
    }

    @Test
    public void testSaveTripError() throws Exception {
        given(tripService.createTrip(trip)).willThrow(new DataIntegrityViolationException("User doesn't exist"));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/trip/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(new ObjectMapper().writeValueAsString(trip));

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("User doesn't exist")
                .build();

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(errorResponse)));
    }


    @Test
    public void testGettingUserTrips() throws Exception {

        given(tripService.getTripsByUserId(1L)).willReturn(Collections.singletonList(trip));
        RequestBuilder request = MockMvcRequestBuilders.get("/trip/user/1");

        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(Collections.singletonList(trip))));
    }

}
