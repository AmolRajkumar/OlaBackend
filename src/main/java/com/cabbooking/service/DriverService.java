package com.cabbooking.service;

import com.cabbooking.exception.DriverExcption;
import com.cabbooking.model.Driver;
import com.cabbooking.model.Ride;
import com.cabbooking.request.DriverSignupRequest;

import java.util.List;

public interface DriverService {


    public Driver registerDriver(DriverSignupRequest driverSignupRequest);

    public List<Driver> getAvailableDrivers(
            double pickupLatitude, double pickupLongitude,
             Ride ride);

    public Driver findNearestDrivers(
            List<Driver> availableDrivers,
            double pickupLatitude, double pickupLongitude);

    public Driver getReqDriverProfile(String jwt) throws DriverExcption;

    public Ride getDriversCurrentRide(Integer driverId) throws DriverExcption;

    public List<Ride> getAllocatedRides(Integer driverId) throws DriverExcption;

    public Driver findDriverById(Integer driverId) throws DriverExcption;

    public List<Ride>completedRides(Integer driverId) throws DriverExcption;



}
