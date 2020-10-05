package com.uber.usersmc.service;

import com.uber.usersmc.entity.Trip;

import java.util.List;

public interface TripInterface {

    public Trip createTrip(Trip user);

    public List<Trip> getTripsByUserId(long id);

}
