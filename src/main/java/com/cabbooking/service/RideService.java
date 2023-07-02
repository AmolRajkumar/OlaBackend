package com.cabbooking.service;

import com.cabbooking.exception.DriverExcption;
import com.cabbooking.exception.RideExcption;
import com.cabbooking.model.Driver;
import com.cabbooking.model.Ride;
import com.cabbooking.model.User;
import com.cabbooking.request.RideRequest;

public interface RideService {


    public void startRide(Integer rideId,int otp) throws RideExcption;

    public Ride requestRide(RideRequest rideRequest, User user) throws DriverExcption;

    public Ride createdRideRequest(User user, Driver nearestDriver, double pickupLatitude, double pickupLongitude, double destinationLatitude, double destinationLongitude, String pickupArea, String destinationArea);

    public void acceptRide(Integer rideId)throws RideExcption;

    public void declineRide(Integer rideId, Integer driverId) throws RideExcption;

    public void completeRide(Integer rideId) throws RideExcption;

    public void cancelRide(Integer rideId) throws RideExcption;

    public Ride findRideById(Integer rideId) throws RideExcption;

}
