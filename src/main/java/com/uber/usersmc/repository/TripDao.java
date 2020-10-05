package com.uber.usersmc.repository;

import com.uber.usersmc.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripDao extends JpaRepository<Trip, Long> {

    public List<Trip> findTripsByUserId(Long userId);

}
