package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RideDetailsRepository extends JpaRepository<RideDetails, Long>  {

    List<RideDetails> findByUserId(long id);

    
    // List<RideDetails> findBypickupLocContaining(String pickupLoc);

    // RideDetails findRideDetailsBypickupLoc(String pickupLoc);

    //get ride details by ride_id
    RideDetails findRideDetailsByRideId(long id);

    

}
