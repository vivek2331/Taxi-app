package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long>{

    //get drivers by ride details id
    // List<Driver> findByRideId(long rideId);

    //get driver by driver id
    Driver findDriverById(long id);
}
