package com.example.demo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ride_details")
public class RideDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ride_id")
    private long rideId;

    @Column(name = "pickup_location",length = 20 )
	private String pickupLoc;

	@Column(name = "destination",length = 20 )
	private String destination;

	@Column(name = "vehicle_type",length = 20 )
	private String vehicleType;

    @Column(name ="price")
    private long price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "rideDetails", cascade = CascadeType.REMOVE)
    private List<Driver> driver;

    public RideDetails(){
        
    }

    public RideDetails(String pickupLoc, String destination, String vehicleType, long price, User user) {
        this.pickupLoc = pickupLoc;
        this.destination = destination;
        this.vehicleType = vehicleType;
        this.price = price;
        this.user = user;
    }

    public long getRideId() {
        return rideId;
    }

    public void setRideId(long rideId) {
        this.rideId = rideId;
    }

    public String getPickupLoc() {
        return pickupLoc;
    }

    public void setPickupLoc(String pickupLoc) {
        this.pickupLoc = pickupLoc;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
   
    

}
