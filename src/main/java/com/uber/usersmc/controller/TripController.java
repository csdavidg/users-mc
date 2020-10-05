package com.uber.usersmc.controller;

import com.uber.usersmc.entity.Trip;
import com.uber.usersmc.service.TripService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/trip")
@AllArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<?> createTrip(@RequestBody Trip trip) {
        return new ResponseEntity<>(tripService.createTrip(trip), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public List<Trip> getTripsByUser(@PathVariable("id") Long userId) {
        return tripService.getTripsByUserId(userId);
    }

}
