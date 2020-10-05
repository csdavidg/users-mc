package com.uber.usersmc.service;

import com.uber.usersmc.repository.TripDao;
import com.uber.usersmc.entity.Trip;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TripService implements TripInterface {

    private final TripDao tripDao;

    @Override
    public Trip createTrip(Trip user) {
        try {
            return tripDao.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("User doesn't exist");
        }
    }

    @Override
    public List<Trip> getTripsByUserId(long id) {
        return tripDao.findTripsByUserId(id);
    }
}
