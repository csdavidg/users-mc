package com.uber.usersmc.service;

import com.uber.usersmc.entity.Trip;
import com.uber.usersmc.entity.User;
import com.uber.usersmc.repository.TripDao;
import com.uber.usersmc.repository.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
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


    @Before
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
    public void testGettingTripsByUser(){
        List<Trip> trips = tripService.getTripsByUserId(user.getId());
        assertEquals(trips.size(), 1);
        assertTrue(trips.contains(trip));
    }


}
