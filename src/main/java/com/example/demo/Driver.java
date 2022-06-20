package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "rideId")
    private RideDetails rideDetails;

    public Driver(){

    }

    public Driver(String name, long phoneNumber, boolean status, RideDetails rideDetails) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.rideDetails = rideDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public RideDetails getRideDetails() {
        return rideDetails;
    }

    public void setRideDetails(RideDetails rideDetails) {
        this.rideDetails = rideDetails;
    }

    
    
}
