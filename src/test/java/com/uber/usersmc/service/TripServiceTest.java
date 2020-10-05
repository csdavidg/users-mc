package com.uber.usersmc.service;

import com.uber.usersmc.entity.Trip;
import com.uber.usersmc.entity.User;
import com.uber.usersmc.repository.TripDao;
import com.uber.usersmc.repository.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@Transactional
@SpringBootTest
public class TripServiceTest {

    @Autowired
    private TripDao tripDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TripService tripService;
    private Trip trip;
    private User user;


    @BeforeEach
    public void setUp() {

        user = User.builder()
                .firstName("Cristian")
                .lastName("Sanchez")
                .phoneNumber(789456123)
                .build();

        userDao.save(user);

        trip = Trip.builder()
                .user(user)
                .origin("home")
                .destiny("work")
                .build();

        tripDao.save(trip);
    }

    @Test
    public void testSavingTrip() {
        Trip tripSave = tripService.createTrip(trip);
        assertNotNull(tripSave);
        assertNotNull(tripSave.getId());
    }

    @Test
    public void testGettingTripsByUser() {
        List<Trip> trips = tripService.getTripsByUserId(user.getId());
        assertEquals(trips.size(), 1);
        assertTrue(trips.contains(trip));
    }


}
